package com.weatherapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.weatherapp.ui.weatherdetails.WeatherDetails

@Composable
fun WeatherAppNavHost(appState: WeatherAppState = rememberWeatherAppState()) {
    NavHost(
        navController = appState.navController,
        startDestination = NavControllerRoute.WeatherDetails.route
    ) {
        composable(route = NavControllerRoute.WeatherDetails.route) {
            WeatherDetails()
        }
    }
}