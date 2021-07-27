package com.ngengeapps.weather.di

import com.ngengeapps.weather.data.remote.WeatherRetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideWeatherService():WeatherRetrofitService = WeatherRetrofitService.create()

}