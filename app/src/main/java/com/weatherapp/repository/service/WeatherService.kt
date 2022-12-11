package com.weatherapp.repository.service

import com.weatherapp.const.APP_ID
import com.weatherapp.const.TEMPERATURE_UNIT
import com.weatherapp.repository.model.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("onecall")
    suspend fun getWeather(
        @Query("lat") latitude : Double,
        @Query("lon") longitude : Double,
        @Query("appid") appId : String = APP_ID,
        @Query("units") temperatureUnit : String = TEMPERATURE_UNIT
    ) : WeatherResponse
}