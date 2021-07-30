package com.ngengeapps.weather.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ngengeapps.weather.R
import com.ngengeapps.weather.WeatherViewModel
import com.ngengeapps.weather.data.models.DailyWeather
import com.ngengeapps.weather.getHourAndMinuteFromTimeStampAndOffSet
import kotlin.math.roundToInt


@Composable
fun DayDetailUI(viewModel: WeatherViewModel,navController: NavController){
    val dailyWeather = viewModel.selectedDayWeather
    val timeZoneOffset = viewModel.timeZoneOffset.observeAsState()

    if (dailyWeather != null && timeZoneOffset.value != null) {
        DayDetailUI(weather = dailyWeather, timeZoneOffset = timeZoneOffset.value!! )
    } else {
        navController.popBackStack()
    }
}

@Composable
fun DayDetailUI(weather: DailyWeather,timeZoneOffset:Long) {
    
    Column(modifier = Modifier.fillMaxSize()) {

        Row(modifier = Modifier.padding(horizontal = 4.dp)) {
            Column {
                Text(text = weather.weather[0].main,fontWeight = FontWeight.Bold)
                Text(text = weather.weather[0].description)
            }

            Spacer(modifier = Modifier.weight(1f))
            Text(text = stringResource(R.string.high_low_temp_celcius,weather.temperature.max.roundToInt(),weather.temperature.min.roundToInt()))
            WeatherIcon(icon = weather.weather[0].icon, size = 30.dp)
        }

        TwoItemText(first = "Precipitation", second = stringResource(R.string.precipitation,weather.rain) )
        Divider()
        TwoItemText(first = "Probability of precipitation", second = weather.precipitationProbability.times(100).roundToInt().toString()+"%" )
        Divider()
        TwoItemText(first = "Wind", second = stringResource(R.string.wind,weather.windSPeed,weather.windDegree) )
        Divider()
        TwoItemText(first = "Pressure", second = "${weather.pressure}hPa")
        Divider()
        TwoItemText(first = "Humidity", second = "${weather.humidity}%")
        Divider()
        TwoItemText(first = "UV index", second = "${weather.uvIndex}")
        Divider()
        TwoItemText(first = "Sunrise", second = getHourAndMinuteFromTimeStampAndOffSet(weather.sunrise,timeZoneOffset) )
        Divider()
        TwoItemText(first = "Sunset", second = getHourAndMinuteFromTimeStampAndOffSet(weather.sunset,timeZoneOffset) )
        Divider()
        TwoItemText(first = "Moon rise", second = getHourAndMinuteFromTimeStampAndOffSet(weather.moonrise,timeZoneOffset) )
        Divider()
        TwoItemText(first = "Moon set", second = getHourAndMinuteFromTimeStampAndOffSet(weather.moonset,timeZoneOffset) )
    }
    
    
    
}