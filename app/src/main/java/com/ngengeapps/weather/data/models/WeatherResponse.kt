package com.ngengeapps.weather.data.models

import com.google.gson.annotations.SerializedName

data class OneCallWeatherResponse(
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lon") val longitude: Double,
    val timeZone: String?,
    @SerializedName("timezone_offset")val timezoneOffset:Long,
    val current: WeatherData,
    val hourly:List<WeatherData>,
    val daily:List<DailyWeather>
)


data class WeatherData(
    val dt:Long,
    val sunrise:Long,
    val sunset:Long,
    @SerializedName("temp")val temperature:Double,
    @SerializedName("feels_like") val feelsLike:Double,
    val pressure:Long,
    val humidity:Long,
    val visibility:Long,
    @SerializedName("wind_speed")val windSPeed:Double,
    @SerializedName("wind_deg") val windDegree:Long,
    val weather:List<Weather>

)

data class DailyWeather(
    val dt: Long,
    val sunset: Long,
    val sunrise: Long,
    val moonrise:Long,
    val moonset:Long,
    @SerializedName("temp")val temperature: Temperature,
    @SerializedName("feels_like")val feelsLikeDay: FeelsLikeDay,
    val pressure: Long,
    val humidity: Long,
    @SerializedName("wind_deg")val windDegree: Long,
    val weather: List<Weather>,
    val rain:Double,
    val clouds:Long

)

data class Temperature(
    val day:Double,
    val min:Double,
    val max:Double,
    val night:Double,
    @SerializedName("eve")val evening:Double,
    @SerializedName("morn")val morning:Double
)
open class FeelsLikeDay(
    val day:Double,
    val night: Double,
    @SerializedName("eve")val evening: Double,
    @SerializedName("morn") val morning: Double
)

data class Weather(
    val id:Long,
    val main:String,
    val description:String,
    val icon:String
)


