package com.ngengeapps.weather.data.repository

import com.ngengeapps.weather.data.models.OneCallWeatherResponse
import com.ngengeapps.weather.data.models.Result
import com.ngengeapps.weather.data.remote.WeatherRetrofitService
import retrofit2.Response

class WeatherRepository(val retrofitService: WeatherRetrofitService) {
    suspend fun getOneApiCall():Response<OneCallWeatherResponse> {
        return retrofitService.oneAPICall()
    }
}