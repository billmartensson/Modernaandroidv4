package se.magictechnology.modernaandroidv4

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request

class APIViewModel : ViewModel() {

    private val _fancytext = MutableStateFlow("START")
    val fancytext: StateFlow<String> = _fancytext.asStateFlow()

    private val _currentJoke = MutableStateFlow<Chuckjoke?>(null)
    val currentJoke: StateFlow<Chuckjoke?> = _currentJoke.asStateFlow()

    private val _errormessage = MutableStateFlow<String?>(null)
    val errormessage: StateFlow<String?> = _errormessage.asStateFlow()

    fun loadFancy() {
        _fancytext.value = "Fancy text"

    }


    fun loadJoke() {

        Thread {
            val client = OkHttpClient()

            val request = Request.Builder()
                .url("https://api.chucknorris.io/jokes/random")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    // FEL
                }

                val jsonstring = response.body!!.string()
                Log.i("modernadebug", jsonstring)

                val thejoke = Json { ignoreUnknownKeys = true }.decodeFromString<Chuckjoke>(jsonstring)
                _currentJoke.value = thejoke
                _errormessage.value = null
                Log.i("modernadebug", thejoke.value)
            }
        }.start()
    }

    fun searchJoke(searchtext : String) {

        Thread {
            val client = OkHttpClient()

            val request = Request.Builder()
                .url("https://api.chucknorris.io/jokes/search?query=" + searchtext)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    // FEL
                }

                val jsonstring = response.body!!.string()
                Log.i("modernadebug", jsonstring)

                val searchresults = Json { ignoreUnknownKeys = true }.decodeFromString<Searchresult>(jsonstring)
                if(searchresults.result.isEmpty()) {
                    _errormessage.value = "No jokes found"
                    _currentJoke.value = null
                } else {
                    _currentJoke.value = searchresults.result.first()
                    _errormessage.value = null
                }

            }
        }.start()
    }

}