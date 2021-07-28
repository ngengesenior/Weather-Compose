package com.ngengeapps.weather

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

const val ICON_BASE_URL ="https://openweathermap.org/img/wn/"
fun getWeatherIcon(icon:String) = "$ICON_BASE_URL$icon@2x.png"
val hourMinuteDateFormat:DateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

fun getDateFormat():DateFormat {
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
    return getDateFormat().format(Date(timestamp+offset))
}

fun getReadableEnglishDate(timestamp: Long):String {
    return getEnglishDateFormat().format(Date(timestamp))
}
