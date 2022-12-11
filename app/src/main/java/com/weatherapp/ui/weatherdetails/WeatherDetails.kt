package com.weatherapp.ui.weatherdetails

import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.weatherapp.ui.utils.Permission

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherDetails(weatherDetailsViewModel: WeatherDetailsViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("currentWeatherPage")
    ) {
        Permission(
            permission = Manifest.permission.ACCESS_COARSE_LOCATION,
            permissionGranted = {

            })
        CurrentWeather()
    }
}