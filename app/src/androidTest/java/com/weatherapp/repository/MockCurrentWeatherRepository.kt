package com.weatherapp.repository

import android.nfc.FormatException
import com.google.gson.JsonSyntaxException
import com.weatherapp.repository.model.LocationData
import com.weatherapp.repository.model.response.CurrentWeather
import com.weatherapp.repository.model.response.Weather
import com.weatherapp.repository.model.response.WeatherResponse
import com.weatherapp.repository.weather.WeatherRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.EOFException
import java.io.IOException

class MockCurrentWeatherRepository(private val responseType: Int? = null) : WeatherRepository() {
    override suspend fun getWeather(locationData: LocationData): NetworkResult<WeatherResponse> {
        return when (responseType) {
            0 -> NetworkResult.Success(
                WeatherResponse(
                    timezone = "Stockholm", current = CurrentWeather(
                        temperature = 12.5,
                        feelsLike = 4.5,
                        weather = listOf(Weather(weatherCode = 800))
                    )
                )
            )
            1 -> NetworkResult.Failure(
                HttpException(
                    Response.error<ResponseBody>(
                        401,
                        ""
                            .toResponseBody("plain/text".toMediaType())
                    )
                )
            )
            2 -> NetworkResult.Failure(FormatException("WrongFormat"))
            3 -> NetworkResult.Failure(EOFException("EOFException"))
            4 -> NetworkResult.Failure(IOException("IOException"))
            5 -> NetworkResult.Failure(JsonSyntaxException("JsonSyntaxException"))

            else -> NetworkResult.Failure(Throwable())
        }

    }
}