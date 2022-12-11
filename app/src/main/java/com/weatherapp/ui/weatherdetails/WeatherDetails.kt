package com.weatherapp.ui.weatherdetails

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.weatherapp.repository.model.LocationData
import com.weatherapp.ui.utils.ErrorDialog
import com.weatherapp.ui.utils.Permission

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherDetails(viewModel: WeatherDetailsViewModel) {

    val weatherData by viewModel.weatherResult.collectAsState()
    val errorDialog by viewModel.isShowErrorDialog.collectAsState()

    val context = LocalContext.current
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    Box {
        if (errorDialog.first) {
            ErrorDialog(errorDialog.second) {
                viewModel.resetErrorDialog()
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .testTag("currentWeatherPage")
        ) {
            Permission(permission = Manifest.permission.ACCESS_COARSE_LOCATION,
                permissionGranted = {
                    if (ActivityCompat.checkSelfPermission(
                            context, Manifest.permission.ACCESS_COARSE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY,
                            object : CancellationToken() {
                                override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                                    CancellationTokenSource().token

                                override fun isCancellationRequested() = false
                            }).addOnSuccessListener { location: Location? ->
                            location?.let {
                                viewModel.getCurrentWeather(
                                    LocationData(
                                        it.latitude, it.longitude
                                    )
                                )
                            }
                        }
                    }
                })
            CurrentWeather(weatherData)
        }
    }
}