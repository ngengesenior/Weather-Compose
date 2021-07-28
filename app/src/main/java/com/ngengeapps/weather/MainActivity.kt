package com.ngengeapps.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.ngengeapps.weather.ui.CurrentConditionUI
import com.ngengeapps.weather.ui.DailyUIList
import com.ngengeapps.weather.ui.HourlyUIList
import com.ngengeapps.weather.ui.LocationPermissionUI
import com.ngengeapps.weather.ui.theme.WeatherTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalFocusManager

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


    var city by remember {
        mutableStateOf("")
    }
    val response by viewModel.data.observeAsState()
    val loading by viewModel.loading.observeAsState(false)
    val currentWeather by viewModel.currentWeatherData.observeAsState(null)
    val hoursList by viewModel.hoursList.observeAsState(null)
    val sevenDaysWeather by viewModel.sevenDaysWeather.observeAsState(null)
    val timeZoneOffset by viewModel.timeZoneOffset.observeAsState(0)

    Column(modifier = Modifier.fillMaxSize()) {

        SearchField(city = city, onValueChange = {
            city = it
        },onSubmit = {
            viewModel.getCoordinatesFromCityNameAndGetWeather(city)
        })


        AnimatedVisibility(visible = loading) {
            CenteredProgressBar(modifier = Modifier.fillMaxSize())
        }

        LocationPermissionUI {
            LazyColumn {
                item {

                    AnimatedVisibility(visible = currentWeather == null) {
                        CenteredProgressBar(modifier = Modifier.fillMaxWidth() )
                    }
                    AnimatedVisibility(visible = currentWeather != null) {
                        CurrentConditionUI(weatherData = currentWeather!!)
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(12.dp))
                }

                item {

                    AnimatedVisibility(visible = hoursList == null) {
                        CenteredProgressBar(modifier = Modifier.fillMaxWidth())
                    }
                    AnimatedVisibility(visible = hoursList != null) {
                        HourlyUIList(hourlyList = hoursList!!, timeZoneOffset = timeZoneOffset)
                    }

                }
                item {
                    Spacer(modifier = Modifier.height(35.dp))
                }

                item {

                    AnimatedVisibility(visible = sevenDaysWeather == null) {
                        CenteredProgressBar(modifier = Modifier.fillMaxWidth())
                    }
                    AnimatedVisibility(visible = hoursList != null) {
                        DailyUIList(dailyList = sevenDaysWeather!!, timeZoneOffset = timeZoneOffset)
                    }

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

@Composable
fun CenteredProgressBar(modifier: Modifier = Modifier) {
    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        CircularProgressIndicator()
    }
}


@Composable
fun SearchField(city:String,onValueChange:(String) ->Unit,onSubmit:()->Unit) {

    val focusManager = LocalFocusManager.current
    OutlinedTextField(modifier = Modifier.fillMaxWidth() ,placeholder = {
        Text(text = "Type City Name")
    } ,singleLine = true,value = city, onValueChange = onValueChange,
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
            onSubmit.invoke()

        }),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Text),
    leadingIcon = {
        Icon(imageVector = Icons.Default.Search, contentDescription = null)
    })
}

