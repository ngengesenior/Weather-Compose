package com.ngengeapps.weather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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

@Composable
fun TwoItemText(modifier:Modifier = Modifier,first:String, second:String) {
    Surface(modifier = Modifier.fillMaxWidth()) {

    }
    Row(modifier = modifier.fillMaxWidth()
        .padding(vertical = 16.dp,horizontal = 4.dp)
    ) {
        Text(text = first)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = second)
    }
}