package com.weatherapp.ui.weatherdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WeatherDetails() {
    Column(modifier = Modifier.fillMaxSize()) {
        CurrentWeather()
    }
}