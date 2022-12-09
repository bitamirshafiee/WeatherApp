package com.weatherapp.ui.weatherdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun WeatherDetails() {
    Column(modifier = Modifier
        .fillMaxSize()
        .testTag("currentWeatherPage")) {
        CurrentWeather()
    }
}