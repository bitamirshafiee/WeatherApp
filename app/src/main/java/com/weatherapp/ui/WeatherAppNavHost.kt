package com.weatherapp.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.weatherapp.di.repository.RepositoryModule
import com.weatherapp.di.weatherdetails.DaggerWeatherDetailsComponent
import com.weatherapp.ui.permissioncheck.PermissionCheck
import com.weatherapp.ui.utils.daggerViewModel
import com.weatherapp.ui.weatherdetails.WeatherDetails
import com.weatherapp.ui.weatherdetails.WeatherDetailsViewModel

@Composable
fun WeatherAppNavHost(appState: WeatherAppState = rememberWeatherAppState()) {

    val context = LocalContext.current

    NavHost(
        navController = appState.navController,
        startDestination = NavControllerRoute.WeatherDetails.route
    ) {
        composable(route = NavControllerRoute.PermissionCheck.route) {
            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            )
            //navigate to location check
            else
                PermissionCheck {
                    //navigate to location check
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