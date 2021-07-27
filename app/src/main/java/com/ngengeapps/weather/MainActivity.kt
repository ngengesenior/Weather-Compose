package com.ngengeapps.weather

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ngengeapps.weather.data.remote.WeatherRetrofitService
import com.ngengeapps.weather.ui.theme.WeatherTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

const val TAG = "AppTag"
class MainActivity : ComponentActivity() {
    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch {

            val retrofitService = WeatherRetrofitService.create()
            try {
                val data = retrofitService.oneAPICall()
                Log.d(TAG, "onCreate: ${data.body()}")
            }catch (ex:Exception) {
                Log.e(TAG, "onCreate: ${ex.message}")
            }
        }
        setContent {
            WeatherTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherTheme {
        Greeting("Android")
    }
}