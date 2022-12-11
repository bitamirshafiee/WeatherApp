package com.weatherapp.ui.weatherdetails

import android.util.Log
import androidx.lifecycle.ViewModel
import com.weatherapp.repository.weather.WeatherRepository

class WeatherDetailsViewModel(weatherRepository: WeatherRepository) : ViewModel() {
    init {
        Log.d("WEATHERVIEWMODEL", "WORKING!")
    }

}