package com.weatherapp.repository.service

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("onecall/{lat}/{lon}/{appid}")
    suspend fun getWeather(
        @Query("lat") latitude : Double,
        @Query("lon") longitude : Double,
        @Query("appid") appId : String
    )
}