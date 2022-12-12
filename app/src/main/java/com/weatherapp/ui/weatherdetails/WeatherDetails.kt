package com.weatherapp.ui.weatherdetails

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.weatherapp.R
import com.weatherapp.ext.checkPermissionStatus
import com.weatherapp.repository.model.LocationData
import com.weatherapp.ui.utils.ErrorDialog

@SuppressLint("MissingPermission")
@Composable
fun WeatherDetails(viewModel: WeatherDetailsViewModel) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    var appStatusInformation by remember { mutableStateOf("Is Loading ...") }
    val weatherData by viewModel.weatherResult.collectAsState()
    val errorDialog by viewModel.isShowErrorDialog.collectAsState()
    val isProgressVisible by viewModel.isInProgress.collectAsState(initial = true)

    val getWeatherInformation: (Location?) -> Unit = {
        it?.let {
            viewModel.getCurrentWeather(
                LocationData(
                    it.latitude, it.longitude
                )
            )
        }
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY,
                object : CancellationToken() {
                    override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                        CancellationTokenSource().token

                    override fun isCancellationRequested() = false
                }).addOnSuccessListener { location: Location? ->
                getWeatherInformation(location)
            }

        } else {
            appStatusInformation = context.getString(R.string.str_feature_is_unavailable)
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                checkPermissionStatus(
                    context = context,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    isGranted = {
                        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY,
                            object : CancellationToken() {
                                override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                                    CancellationTokenSource().token

                                override fun isCancellationRequested() = false
                            }).addOnSuccessListener { location: Location? ->
                            getWeatherInformation(location)
                        }
                    },
                    showRational = {
                        appStatusInformation =
                            context.getString(R.string.str_why_we_need_permission)
                        launcher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                    },
                    isNotGranted = {
                        launcher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                    })
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
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
                    Text(
                        text = appStatusInformation,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                            .wrapContentHeight(Alignment.CenterVertically)
                            .testTag("prog")
                    )
                else
                    CurrentWeather(weatherData)
            }
        }
    }
}