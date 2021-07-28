package com.ngengeapps.weather

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationServices
import com.ngengeapps.weather.data.models.Coordinates
import com.ngengeapps.weather.data.models.DailyWeather
import com.ngengeapps.weather.data.models.OneCallWeatherResponse
import com.ngengeapps.weather.data.models.WeatherData
import com.ngengeapps.weather.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository,
                                           @ApplicationContext context:Context,val geocoder: Geocoder):ViewModel() {
    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)


    val errorMessage = MutableLiveData<String>()
    val data = MutableLiveData<OneCallWeatherResponse>()
    val hoursList = MutableLiveData<List<WeatherData>>()
    val currentWeatherData = MutableLiveData<WeatherData>()
    val timeZoneOffset = MutableLiveData<Long>(0)
    val loading = MutableLiveData<Boolean>()
    val sevenDaysWeather = MutableLiveData<List<DailyWeather>>()
    val coordinates = MutableLiveData<Coordinates>()
    var job:Job? =  null
    init {

        requestLastLocationAndInitWeather()
    }
     fun getWeather(coordinates: Coordinates){
        loading.value = true
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getOneApiCall(coordinates.latitude,coordinates.longitude)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful){
                    val responseBody = response.body()!!
                    data.value = responseBody
                    Log.d(TAG, "getWeather: ${response.body()}")
                    timeZoneOffset.value = responseBody.timezoneOffset
                    hoursList.value = response.body()!!.hourly
                    currentWeatherData.value = responseBody.current
                    sevenDaysWeather.value = responseBody.daily
                    loading.value = false
                } else {
                    onErrorMessage("Error: ${response.message()}")
                    Log.e(TAG, "getWeather: ${response.message()}")
                    loading.value = false
                }

            }
        }


    }

    private fun onErrorMessage(message:String){

        Log.e(TAG, "onErrorMessage: $message", )
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    @SuppressLint("MissingPermission")
    fun requestLastLocationAndInitWeather() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location:Location? ->

            location?.let {
                coordinates.value = Coordinates(it.latitude,it.longitude)
                Log.d(TAG, "requestLastLocationAndInitWeather: ${location.latitude}, ${location.longitude}")
                getWeather(Coordinates(it.latitude,it.longitude))
            }
        }.addOnFailureListener {
            onErrorMessage("${it.message}")
        }
    }

    fun getCoordinatesFromCityNameAndGetWeather(cityName:String) {
        loading.value = true
        try {
            val locations = geocoder.getFromLocationName(cityName,1)
            if (locations.isNotEmpty()) {
                val loc = locations[0]
                val coords = Coordinates(loc.latitude,loc.longitude)
                getWeather(coords)
            } else {
                onErrorMessage("No valid location was found")

            }
        } catch (ex:Exception) {

            onErrorMessage("Error:${ex.message}")
        }

    }

}