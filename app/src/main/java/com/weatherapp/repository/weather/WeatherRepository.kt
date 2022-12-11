package com.weatherapp.repository.weather

import com.weatherapp.repository.NetworkResult
import com.weatherapp.repository.model.LocationData
import com.weatherapp.repository.model.WeatherResponse
import com.weatherapp.repository.service.ServiceProvider
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
                        locationData.latitude,
                        locationData.longitude
                    )
                )
            } catch (exception: Exception) {
                NetworkResult.Failure(exception)
            }
        }
    }
}