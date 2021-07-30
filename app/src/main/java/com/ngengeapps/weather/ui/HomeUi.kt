package com.ngengeapps.weather.ui

import CurrentConditionUI
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.ngengeapps.weather.WeatherViewModel
import com.ngengeapps.weather.data.models.DailyWeather

@ExperimentalPermissionsApi
@ExperimentalAnimationApi
@Composable
fun WeatherUI(viewModel: WeatherViewModel,
              navController: NavController
) {

    var city by remember {
        mutableStateOf("")
    }
    val error by viewModel.errorMessage.observeAsState()
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
        
        if (error != null) {
            ErrorUI(message = error!!,modifier = Modifier.fillMaxSize())
        }


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
                        DailyUIList(dailyList = sevenDaysWeather!!, timeZoneOffset = timeZoneOffset,onNavigateToDetail = { dailyWeather: DailyWeather, l: Long ->
                            viewModel.selectDayDetails(dailyWeather)
                            viewModel.selectOffset(l)
                            navController.navigate(Screen.DayDetails.route)

                        })
                    }

                }


            }
        }

    }

}