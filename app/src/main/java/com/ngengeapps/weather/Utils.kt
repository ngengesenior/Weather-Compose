package com.ngengeapps.weather

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val ICON_BASE_URL ="https://openweathermap.org/img/wn/"
fun getWeatherIcon(icon:String) = "$ICON_BASE_URL$icon@2x.png"

fun getHourAndMinuteFormat():DateFormat {
    val hourMinuteDateFormat:DateFormat = SimpleDateFormat("HH:mm")
    hourMinuteDateFormat.timeZone = TimeZone.getTimeZone("GMT")
    return hourMinuteDateFormat

}

fun getEnglishDateFormat():DateFormat {
    return SimpleDateFormat("EEE, d MMM",Locale.getDefault())
}

fun Context.openSettings() {

    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    val uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivity(intent)
}

fun getHourAndMinuteFromTimeStampAndOffSet(
    timestamp:Long,
    offset:Long
):String {
    return getHourAndMinuteFormat().format(Date((timestamp+offset) * 1000))
}

fun getReadableEnglishDate(seconds: Long,offset: Long):String {
    return getEnglishDateFormat().format(Date((seconds+offset) * 1000))
}

