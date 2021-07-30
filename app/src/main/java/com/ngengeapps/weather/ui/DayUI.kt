package com.ngengeapps.weather.ui
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ngengeapps.weather.R
import com.ngengeapps.weather.data.models.DailyWeather
import com.ngengeapps.weather.getReadableEnglishDate
import kotlin.math.roundToInt

@Composable
fun DailyUIList(dailyList:List<DailyWeather>,
                timeZoneOffset:Long,onNavigateToDetail:(DailyWeather,Long)->Unit){


    Column(modifier = Modifier
        .fillMaxWidth()
    ) {
        dailyList.forEach {
            val time = getReadableEnglishDate(it.dt,timeZoneOffset)
            DayUIItem(modifier = Modifier.clickable {
                        onNavigateToDetail(it,timeZoneOffset)
            } ,dailyWeather = it, timeText = time)
            Divider()
        }

    }
}

@Composable
fun DayUIItem(modifier: Modifier = Modifier,dailyWeather: DailyWeather, timeText:String) {
    Surface(modifier = modifier.padding(vertical = 16.dp,horizontal = 3.dp)) {
        Row{
            Text(text = timeText)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "${dailyWeather.temperature.max.roundToInt()}" + "/"+ "${dailyWeather.temperature.min.roundToInt()}°C")
            Spacer(modifier = Modifier.width(4.dp))
            WeatherIcon(icon = dailyWeather.weather[0].icon, size = 30.dp)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(painter = painterResource(R.drawable.arrow_right), contentDescription = null)
        }
    }
}