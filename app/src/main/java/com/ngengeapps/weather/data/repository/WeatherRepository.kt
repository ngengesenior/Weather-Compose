package com.ngengeapps.weather.data.repository

import com.ngengeapps.weather.data.models.OneCallWeatherResponse
import com.ngengeapps.weather.data.models.Result
import com.ngengeapps.weather.data.remote.WeatherRetrofitService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(private val retrofitService: WeatherRetrofitService) {
    suspend fun getOneApiCall():Response<OneCallWeatherResponse> {
        return retrofitService.oneAPICall()
    }
}