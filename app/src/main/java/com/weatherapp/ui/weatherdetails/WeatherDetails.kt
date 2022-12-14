package com.weatherapp.ui.weatherdetails

import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.weatherapp.repository.model.LocationData
import com.weatherapp.ui.utils.CircularProgress
import com.weatherapp.ui.utils.ErrorDialog


@SuppressLint("MissingPermission")
@Composable
fun WeatherDetails(viewModel: WeatherDetailsViewModel) {

    val context = LocalContext.current
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    val weatherData by viewModel.weatherResult.collectAsState()
    val errorDialog by viewModel.isShowErrorDialog.collectAsState()
    val isProgressVisible by viewModel.isInProgress.collectAsState(initial = true)

    val getWeatherInformation: (Location?) -> Unit = { location ->
        location?.let { value ->
            viewModel.getCurrentWeather(
                LocationData(
                    value.latitude, value.longitude
                )
            )
        }
    }
    LaunchedEffect(Unit) {
        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            object : CancellationToken() {
                override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                    CancellationTokenSource().token

                override fun isCancellationRequested() = false
            }).addOnSuccessListener { location: Location? ->
            getWeatherInformation(location)
        }
    }

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

            Box(modifier = Modifier) {
                if (isProgressVisible)
                    CircularProgress()
                else CurrentWeather(weatherData)
            }
        }
    }
}

