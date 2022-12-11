package com.weatherapp.repository.model.response

import com.google.gson.annotations.SerializedName

data class WeatherResponse(val timezone: String, val current: CurrentWeather)

fun getDefaultWeatherResponse() =
    WeatherResponse(timezone = "", current = CurrentWeather(temperature = 0.0, feelsLike = 0.0))

data class CurrentWeather(
    @SerializedName("temp") val temperature: Double,
    @SerializedName("feels_like") val feelsLike: Double
)