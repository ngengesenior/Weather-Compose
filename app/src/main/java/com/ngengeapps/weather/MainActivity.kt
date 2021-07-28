package com.ngengeapps.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.ngengeapps.weather.ui.CurrentConditionUI
import com.ngengeapps.weather.ui.HourlyUIList
import com.ngengeapps.weather.ui.LocationPermissionUI
import com.ngengeapps.weather.ui.theme.WeatherTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi

const val TAG = "AppTag"
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel:WeatherViewModel by viewModels()
    @ExperimentalPermissionsApi
    @ExperimentalAnimationApi
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



@ExperimentalPermissionsApi
@ExperimentalAnimationApi
@Composable
fun WeatherUI(viewModel:WeatherViewModel) {

    val response by viewModel.data.observeAsState()
    val loading by viewModel.loading.observeAsState(false)
    val timeZoneOffset by viewModel.timeZoneOffset.observeAsState(0)


    AnimatedVisibility(visible = loading) {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
            CircularProgressIndicator()
        }
    }

    LocationPermissionUI {
        LazyColumn {
            item {
                response?.current?.let {
                    CurrentConditionUI(weatherData = it)
                }
            }
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                response?.hourly?.let {
                    HourlyUIList(hourlyList = it,timeZoneOffset = timeZoneOffset)
                }
            }


        }
    }


    //CurrentConditionUI(weatherData = response?.current)

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherTheme {

    }
}