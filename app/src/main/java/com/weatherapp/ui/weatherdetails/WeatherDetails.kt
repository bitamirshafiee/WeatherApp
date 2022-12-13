package com.weatherapp.ui.weatherdetails

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.weatherapp.ext.isLocationServiceEnabled
import com.weatherapp.repository.model.LocationData
import com.weatherapp.ui.utils.ErrorDialog
import com.weatherapp.ui.utils.OpenSettingsDialogCamera
import com.weatherapp.ui.utils.PermissionHelper


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherDetails(viewModel: WeatherDetailsViewModel) {

    val context = LocalContext.current
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    val appStatusInformation by remember { mutableStateOf("Is Loading ...") }
    val weatherData by viewModel.weatherResult.collectAsState()
    val errorDialog by viewModel.isShowErrorDialog.collectAsState()
    var enableLocationDialog by remember { mutableStateOf(!isLocationServiceEnabled(context = context)) }
    var locationEnabled by remember { mutableStateOf(isLocationServiceEnabled(context = context)) }
    val isProgressVisible by viewModel.isInProgress.collectAsState(initial = true)
    var isPermissionGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }


    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->

        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    val getWeatherInformation: (Location?) -> Unit = {
        it?.let {
            viewModel.getCurrentWeather(
                LocationData(
                    it.latitude, it.longitude
                )
            )
        }
    }
    LaunchedEffect(key1 = isPermissionGranted,locationEnabled) {
        if (isPermissionGranted && locationEnabled)
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
        PermissionHelper(permission = Manifest.permission.ACCESS_COARSE_LOCATION,
            permissionGranted = {
                isPermissionGranted = true
            },
            locationEnabled = {
                locationEnabled = true
            })

    Box {
        if (errorDialog.first) {
            ErrorDialog(errorDialog.second) {
                viewModel.resetErrorDialog()
            }
        }
        if (enableLocationDialog) OpenSettingsDialogCamera {
            enableLocationDialog = false
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .testTag("currentWeatherPage")
        ) {

            Box(modifier = Modifier) {
                if (isProgressVisible) Text(
                    text = appStatusInformation,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .wrapContentHeight(Alignment.CenterVertically)
                        .testTag("prog")
                )
                else CurrentWeather(weatherData)
            }
        }
    }
}

