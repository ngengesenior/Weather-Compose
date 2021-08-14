package com.ngengeapps.weather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.ngengeapps.weather.R
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
fun ErrorUI(message:String,modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(R.drawable.error_96), contentDescription = null)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = message)

    }
}

@Composable
fun BoldText(text:String) {
    Text(text = text,fontWeight = FontWeight.Bold,fontSize = 10.sp)
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


/**
 * The icon is the string returned from OpenWeatherMap API
 */
@Composable
fun WeatherIcon(icon:String,size: Dp){
    Image(painter = rememberImagePainter(data = getWeatherIcon(icon),
        builder = {
            crossfade(true)
            transformations(CircleCropTransformation())
        }) , contentDescription = null,
        modifier = Modifier.size(size))
}


/**
 * Essentially, this is a row of two [Text] with padding separated by a [Spacer]
 * It is essential because most of the UI elements in the Detail screen are similar
 */
@Composable
fun TwoItemText(modifier:Modifier = Modifier,first:String, second:String) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp, horizontal = 4.dp)
    ) {
        Text(text = first)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = second)
    }
}