package com.ngengeapps.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ngengeapps.weather.data.models.OneCallWeatherResponse
import com.ngengeapps.weather.data.repository.WeatherRepository
import kotlinx.coroutines.*

class WeatherViewModel(private val repository: WeatherRepository):ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val data = MutableLiveData<OneCallWeatherResponse>()
    val loading = MutableLiveData<Boolean>()
    var job:Job? =  null
    fun getWeather(){
        loading.value = true
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getOneApiCall()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful){
                    data.value = response.body()!!
                    loading.value = false
                } else {
                    onErrorMessage("Error: ${response.message()}")
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