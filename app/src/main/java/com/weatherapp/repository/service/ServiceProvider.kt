package com.weatherapp.repository.service

import retrofit2.Retrofit

class ServiceProvider(retrofit: Retrofit) {
    val weatherService: WeatherService = retrofit.create(WeatherService::class.java)
}