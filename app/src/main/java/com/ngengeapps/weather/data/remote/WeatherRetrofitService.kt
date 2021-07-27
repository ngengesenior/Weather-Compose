package com.ngengeapps.weather.data.remote

import com.ngengeapps.weather.data.models.OneCallWeatherResponse
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherRetrofitService {

    @GET("onecall")
    suspend fun oneAPICall(@Query("lat") latitude:Double = bueaLat ,
                           @Query("lon") longitude:Double = bueaLon,
                           @Query("appid") apiKey:String = testAPIKey):Response<OneCallWeatherResponse>



    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        val bueaLat = 4.1560
        val bueaLon = 9.2632
        val testAPIKey = "d863477b1f9767da6d0a4e9a95ee37a9"


        private fun create(httpUrl: HttpUrl):WeatherRetrofitService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()


            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(WeatherRetrofitService::class.java)

        }

        fun create():WeatherRetrofitService = create(BASE_URL.toHttpUrlOrNull()!!)

    }

}