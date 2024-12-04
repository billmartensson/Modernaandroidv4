package se.magictechnology.modernaandroidv4

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import se.magictechnology.modernaandroidv4.ui.theme.Modernaandroidv4Theme

@Serializable
data class Chuckjoke(val id : String, val value : String)

@Serializable
data class Searchresult(val total : Int, val result : List<Chuckjoke>)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Modernaandroidv4Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

fun doapi() {

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

            Log.i("modernadebug", thejoke.value)
        }
    }.start()
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )

        Button(onClick = {
            doapi()
        }) {
            Text(text = "DO API")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Modernaandroidv4Theme {
        Greeting("Android")
    }
}