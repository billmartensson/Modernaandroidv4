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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import se.magictechnology.modernaandroidv4.ui.theme.Modernaandroidv4Theme

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



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    val viewModel: APIViewModel = viewModel()

    val fancytext by viewModel.fancytext.collectAsState()
    val currentjoke by viewModel.currentJoke.collectAsState()
    val errormessage by viewModel.errormessage.collectAsState()

    var searchtext by remember { mutableStateOf("") }

    Column {

        if(errormessage != null) {
            Text(errormessage!!, fontSize = 24.sp, modifier = Modifier.padding(10.dp))
        }

        TextField(
            value = searchtext,
            onValueChange = { searchtext = it },
            label = { Text("Search") }
        )
        Button(onClick = {
            viewModel.searchJoke(searchtext)
        }) {
            Text(text = "Search")
        }

        if(currentjoke != null) {
            Text(currentjoke!!.value)
        }

        Button(onClick = {
            viewModel.loadJoke()
        }) {
            Text(text = "New joke!")
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