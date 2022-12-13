package com.weatherapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.weatherapp.di.repository.RepositoryModule
import com.weatherapp.di.weatherdetails.DaggerWeatherDetailsComponent
import com.weatherapp.ui.locationcheck.LocationCheck
import com.weatherapp.ui.permissioncheck.PermissionCheck
import com.weatherapp.ui.utils.daggerViewModel
import com.weatherapp.ui.weatherdetails.WeatherDetails
import com.weatherapp.ui.weatherdetails.WeatherDetailsViewModel

@Composable
fun WeatherAppNavHost(appState: WeatherAppState = rememberWeatherAppState()) {

    NavHost(
        navController = appState.navController,
        startDestination = NavControllerRoute.PermissionCheck.route,
        modifier = Modifier.padding(16.dp)
    ) {
        composable(route = NavControllerRoute.PermissionCheck.route) {
            PermissionCheck(navigateToCheckIfLocationIsEnabled = {
                appState.navigateTo(NavControllerRoute.LocationCheck.route)
            })
        }
        composable(route = NavControllerRoute.LocationCheck.route) {
            LocationCheck {
                appState.navigateTo(NavControllerRoute.WeatherDetails.route)
            }
        }
        composable(route = NavControllerRoute.WeatherDetails.route) {
            val component =
                DaggerWeatherDetailsComponent.builder().repositoryModule(RepositoryModule()).build()

            val viewModel: WeatherDetailsViewModel = daggerViewModel {
                component.getViewModel()
            }
            WeatherDetails(viewModel)
        }
    }
}