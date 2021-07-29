package com.ngengeapps.weather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.ngengeapps.weather.getWeatherIcon

@Composable
fun CenteredProgressBar(modifier: Modifier = Modifier) {
    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun BoldText(text:String) {
    Text(text = text,fontWeight = FontWeight.Bold,fontSize = 10.sp)
}

@Composable
fun WeatherIcon(icon:String,size: Dp){
    Image(painter = rememberImagePainter(data = getWeatherIcon(icon),
        builder = {
            crossfade(true)
            transformations(CircleCropTransformation())
        }) , contentDescription = null,
        modifier = Modifier.size(size))
}
