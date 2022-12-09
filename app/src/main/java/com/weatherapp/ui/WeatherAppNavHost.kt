package com.weatherapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost

@Composable
fun WeatherAppNavHost(appState: WeatherAppState = rememberWeatherAppState()) {
    NavHost(
        navController = appState.navController,
        startDestination = NavControllerRoute.WeatherDetails.route
    ) {

    }
}