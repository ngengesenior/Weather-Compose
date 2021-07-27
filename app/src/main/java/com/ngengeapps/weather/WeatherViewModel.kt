package com.ngengeapps.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ngengeapps.weather.data.models.OneCallWeatherResponse
import com.ngengeapps.weather.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository):ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val data = MutableLiveData<OneCallWeatherResponse>()
    val loading = MutableLiveData<Boolean>()
    var job:Job? =  null
    init {
        getWeather()
    }
    fun getWeather(){
        loading.value = true
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getOneApiCall()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful){
                    data.value = response.body()!!
                    Log.d(TAG, "getWeather: ${response.body()}")
                    loading.value = false
                } else {
                    onErrorMessage("Error: ${response.message()}")
                    Log.e(TAG, "getWeather: ${response.message()}")
                }

            }
        }


    }

    private fun onErrorMessage(message:String){

        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}