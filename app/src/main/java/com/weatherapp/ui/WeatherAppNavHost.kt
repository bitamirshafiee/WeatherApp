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
import com.weatherapp.ext.isLocationServiceEnabled
import com.weatherapp.ui.locationcheck.LocationCheck
import com.weatherapp.ui.permissioncheck.PermissionCheck
import com.weatherapp.ui.utils.daggerViewModel
import com.weatherapp.ui.weatherdetails.WeatherDetails
import com.weatherapp.ui.weatherdetails.WeatherDetailsViewModel

@Composable
fun WeatherAppNavHost(appState: WeatherAppState = rememberWeatherAppState()) {

    val context = LocalContext.current

    NavHost(
        navController = appState.navController,
        startDestination = NavControllerRoute.PermissionCheck.route
    ) {
        composable(route = NavControllerRoute.PermissionCheck.route) {
            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            )
            appState.navigateTo(NavControllerRoute.LocationCheck.route)
            else
                PermissionCheck {
                    appState.navigateTo(NavControllerRoute.LocationCheck.route)
                }
        }

        composable(route = NavControllerRoute.LocationCheck.route) {

            if (isLocationServiceEnabled(context = context)) {
                //navigate to weather details with lat and lon
            } else {
                LocationCheck {
                    //navigate to weather details with lat and lon
                }
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