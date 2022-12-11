package com.weatherapp.repository.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(val timezone: String, val current: CurrentWeather)

data class CurrentWeather(
    @SerializedName("temp") val temperature: Double,
    @SerializedName("feels_like") val feelsLike: Double
)