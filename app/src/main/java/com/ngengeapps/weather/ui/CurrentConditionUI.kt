package com.ngengeapps.weather.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.ngengeapps.weather.data.models.DailyWeather
import com.ngengeapps.weather.data.models.Weather
import com.ngengeapps.weather.data.models.WeatherData
import com.ngengeapps.weather.getHourAndMinuteFromTimeStampAndOffSet
import com.ngengeapps.weather.getReadableEnglishDate
import com.ngengeapps.weather.getWeatherIcon
import com.ngengeapps.weather.hourMinuteDateFormat
import java.util.*


@Composable
fun CurrentConditionUI(weatherData: WeatherData){
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        MainConditionUI(weather = weatherData.weather[0],modifier = Modifier.fillMaxWidth())
        Text(text = "${weatherData.temperature}"+ "\u00B0 F",fontWeight = FontWeight.Bold,textAlign = TextAlign.Center,fontSize = 40.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Feels like ${weatherData.feelsLike}"+ "\u00B0 F",style = MaterialTheme.typography.caption,fontSize = 10.sp)
        Spacer(modifier = Modifier.height(45.dp))
        GeneralWeatherInfoUI(currentWeather = weatherData)
    }
    
}

@Composable
fun MainConditionUI(weather:Weather,modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.Top) {
        WeatherIcon(icon = weather.icon, size = 60.dp )
        Column(modifier = Modifier.padding(top = 10.dp)) {
            Text(text = weather.main,fontWeight = FontWeight.SemiBold)
            Text(text = weather.description,style = MaterialTheme.typography.caption)
        }
    }
}

@Composable
fun GeneralWeatherInfoUI(currentWeather:WeatherData) {

    Surface(shape = RoundedCornerShape(8.dp),color = Color.LightGray,
    modifier = Modifier.padding(horizontal = 4.dp)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 8.dp)
        ) {
            
            Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                BoldText(text = "Wind:${currentWeather.windSPeed}m/s ${currentWeather.windDegree}")
                BoldText(text = "Humidity:${currentWeather.humidity}%")
                BoldText(text = "UV Index:${currentWeather.uvIndex}")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                BoldText(text = "Pressure:${currentWeather.pressure}hPa")
                BoldText(text = "Visibility:${currentWeather.visibility}m")
                BoldText(text = "Dew point:${currentWeather.dewPoint}"+ "\u00B0 F")
            }



        }
    }

    
    
}


@Composable
fun HourlyUIList(hourlyList:List<WeatherData>,timeZoneOffset:Long){
    Log.d("APPTag", "HourlyUIList: $hourlyList")
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
fun HourlyUIItem(hourData:WeatherData,timeText:String) {
    Column() {
        Text(text = timeText)
        Spacer(modifier = Modifier.height(4.dp))
        WeatherIcon(icon = hourData.weather[0].icon, size = 40.dp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "${hourData.temperature}" + "° C")

    }
}

@Composable
fun BoldText(text:String) {
    Text(text = text,fontWeight = FontWeight.Bold,fontSize = 10.sp)
}

@Composable
fun WeatherIcon(icon:String,size:Dp){
    Image(painter = rememberImagePainter(data = getWeatherIcon(icon),
        builder = {
            crossfade(true)
            transformations(CircleCropTransformation())
        }) , contentDescription = null,
        modifier = Modifier.size(size))
}

@Composable
fun DailyUIList(dailyList:List<DailyWeather>,timeZoneOffset:Long){
    Log.d("APPTag", "HourlyUIList: $dailyList")
    Column(modifier = Modifier
        .fillMaxWidth()
    ) {

        dailyList.forEach {
            val time = getReadableEnglishDate(it.dt)
            DayUIItem(dailyWeather = it, timeText = time )
            Divider()
        }

    }
}

@Composable
fun DayUIItem(dailyWeather: DailyWeather,timeText:String) {

    Surface(modifier = Modifier.padding(vertical = 16.dp,horizontal = 3.dp)) {
        Row() {
            Text(text = timeText)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "${dailyWeather.temperature.max}" + "/"+ "${dailyWeather.temperature.min}")
            Spacer(modifier = Modifier.width(4.dp))
            WeatherIcon(icon = dailyWeather.weather[0].icon, size = 30.dp)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(Icons.Default.ArrowForward, contentDescription = null)
        }
    }



}

