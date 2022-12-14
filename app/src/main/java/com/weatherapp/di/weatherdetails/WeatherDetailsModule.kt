package com.weatherapp.di.weatherdetails

import com.weatherapp.repository.weather.WeatherRepository
import com.weatherapp.ui.weatherdetails.WeatherDetailsViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WeatherDetailsModule {

    @Provides
    @Singleton
    fun provideWeatherDetailsViewModel(weatherRepository: WeatherRepository): WeatherDetailsViewModel =
        WeatherDetailsViewModel(weatherRepository)
}