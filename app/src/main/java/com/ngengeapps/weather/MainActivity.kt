package com.ngengeapps.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.ngengeapps.weather.ui.CurrentConditionUI
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
            WeatherTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    WeatherUI(viewModel = viewModel)
                }
            }
        }
    }
}



@Composable
fun WeatherUI(viewModel:WeatherViewModel) {
    val response by viewModel.data.observeAsState()
    response?.current?.let {
        CurrentConditionUI(weatherData = it)
    }
    //CurrentConditionUI(weatherData = response?.current)

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherTheme {

    }
}