package com.ngengeapps.weather.di

import com.ngengeapps.weather.data.remote.WeatherRetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.text.DateFormat
import java.text.SimpleDateFormat
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideWeatherService():WeatherRetrofitService = WeatherRetrofitService.create()

    @Singleton
    @Provides
    fun provideHourMinuteDateFormat():DateFormat = SimpleDateFormat("HH:mm")

}