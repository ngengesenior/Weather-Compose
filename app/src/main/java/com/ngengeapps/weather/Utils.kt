package com.ngengeapps.weather

const val ICON_BASE_URL ="https://openweathermap.org/img/wn/"
fun getWeatherIcon(icon:String) = "$ICON_BASE_URL$icon@2x.png"
