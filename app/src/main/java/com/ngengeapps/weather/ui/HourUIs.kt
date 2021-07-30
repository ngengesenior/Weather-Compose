package com.ngengeapps.weather.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ngengeapps.weather.R
import com.ngengeapps.weather.data.models.WeatherData
import com.ngengeapps.weather.getHourAndMinuteFromTimeStampAndOffSet
import kotlin.math.roundToInt

@Composable
fun HourlyUIList(hourlyList:List<WeatherData>, timeZoneOffset:Long){
    LazyRow(modifier = Modifier
        .fillMaxWidth()
    ) {
        items(items = hourlyList){ item ->
            val time = getHourAndMinuteFromTimeStampAndOffSet(item.dt,timeZoneOffset)
            HourlyUIItem(hourData = item,timeText = time)
            Spacer(modifier = Modifier.width(6.dp))
        }

    }
}

@Composable
fun HourlyUIItem(hourData: WeatherData, timeText:String) {
    Column() {
        Text(text = timeText)
        Spacer(modifier = Modifier.height(4.dp))
        WeatherIcon(icon = hourData.weather[0].icon, size = 40.dp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = stringResource(R.string.temperature, hourData.temperature.roundToInt() ))

    }
}