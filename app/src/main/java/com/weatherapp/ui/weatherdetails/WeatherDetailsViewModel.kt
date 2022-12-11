package com.weatherapp.ui.weatherdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.ext.errorHandlerHelper
import com.weatherapp.repository.NetworkResult
import com.weatherapp.repository.model.LocationData
import com.weatherapp.repository.model.response.WeatherResponse
import com.weatherapp.repository.model.response.getDefaultWeatherResponse
import com.weatherapp.repository.weather.WeatherRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherDetailsViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    private val _isInProgress = MutableStateFlow(false)
    val isInProgress: StateFlow<Boolean> = _isInProgress

    private val _weatherResult = MutableStateFlow(getDefaultWeatherResponse())
    val weatherResult: StateFlow<WeatherResponse> = _weatherResult

    private val _isShowErrorDialog = MutableStateFlow(Pair(false, -1))
    val isShowErrorDialog: StateFlow<Pair<Boolean, Int>> = _isShowErrorDialog

    fun resetErrorDialog(){
        _isShowErrorDialog.value = Pair(false,-1)
    }

    fun getCurrentWeather(locationData: LocationData) {
        viewModelScope.launch {
            when (val result = weatherRepository.getWeather(locationData)) {
                is NetworkResult.Success -> {
                    _weatherResult.value = result.data
                }
                is NetworkResult.Failure -> {
                    val networkError = errorHandlerHelper(result.throwable)
                    _isShowErrorDialog.value = Pair(true, networkError.errorMessage)
                }
            }
        }
    }

}