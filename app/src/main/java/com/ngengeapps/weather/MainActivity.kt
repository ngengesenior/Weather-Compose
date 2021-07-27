package com.ngengeapps.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ngengeapps.weather.ui.theme.WeatherTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi

const val TAG = "AppTag"
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel:WeatherViewModel by viewModels()
    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        /*GlobalScope.launch {

            val retrofitService = WeatherRetrofitService.create()
            try {
                val data = retrofitService.oneAPICall()
                Log.d(TAG, "onCreate: ${data.body()}")
            }catch (ex:Exception) {
                Log.e(TAG, "onCreate: ${ex.message}")
            }
        }*/
        setContent {
            viewModel.getWeather()
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