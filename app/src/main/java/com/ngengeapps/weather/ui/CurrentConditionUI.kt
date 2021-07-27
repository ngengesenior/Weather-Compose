package com.ngengeapps.weather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.ngengeapps.weather.data.models.Weather
import com.ngengeapps.weather.data.models.WeatherData
import com.ngengeapps.weather.getWeatherIcon


@Composable
fun CurrentConditionUI(weatherData: WeatherData){
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        MainConditionUI(weather = weatherData.weather[0],modifier = Modifier.fillMaxWidth())
        Text(text = "${weatherData.temperature}"+ "\u00B0 F",fontWeight = FontWeight.Bold,textAlign = TextAlign.Center,fontSize = 40.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Feels like ${weatherData.feelsLike}"+ "\u00B0 F",style = MaterialTheme.typography.caption,fontSize = 10.sp)
        GeneralWeatherInfoUI(currentWeather = weatherData)
    }
    
}

@Composable
fun MainConditionUI(weather:Weather,modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.Top) {
        Image(painter = rememberImagePainter(data = getWeatherIcon(weather.icon),
        builder = {
            crossfade(true)
            transformations(CircleCropTransformation())
        }) , contentDescription = null,
        modifier = Modifier.size(50.dp))
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
            .padding(vertical = 10.dp, horizontal = 8.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                BoldText(text = "Wind:${currentWeather.windSPeed}m/s ${currentWeather.windDegree}")
                BoldText(text = "Humidity:${currentWeather.humidity}%")
                BoldText(text = "UV Index:${currentWeather.uvIndex}")
            }

            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                BoldText(text = "Pressure:${currentWeather.pressure}hPa")
                BoldText(text = "Visibility:${currentWeather.visibility}km")
                BoldText(text = "Dew point:${currentWeather.dewPoint}"+ "\u00B0 F")
            }


        }
    }

    
    
}

@Composable
fun BoldText(text:String) {
    Text(text = text,fontWeight = FontWeight.SemiBold,fontSize = 10.sp)
}

