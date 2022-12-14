package com.weatherapp.repository.weather

import com.weatherapp.repository.NetworkResult
import com.weatherapp.repository.model.LocationData
import com.weatherapp.repository.model.response.WeatherResponse
import com.weatherapp.repository.service.ServiceProvider
import com.weatherapp.ext.format
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class WeatherRepository {
    abstract suspend fun getWeather(locationData: LocationData): NetworkResult<WeatherResponse>
}

class WeatherRepositoryImpl(serviceProvider: ServiceProvider) : WeatherRepository() {

    private val weatherService = serviceProvider.weatherService

    override suspend fun getWeather(locationData: LocationData): NetworkResult<WeatherResponse> {
        return withContext(Dispatchers.IO) {
            try {
                NetworkResult.Success(
                    weatherService.getWeather(
                        locationData.latitude.format(2),
                        locationData.longitude.format(2)
                    )
                )
            } catch (exception: Exception) {
                NetworkResult.Failure(exception)
            }
        }
    }
}