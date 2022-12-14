package com.weatherapp.ext

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import com.weatherapp.R
import com.weatherapp.ui.WeatherActivity
import kotlin.Exception

fun chooseDrawableAccordingToWeatherState(weatherCode: Int): Int {

    return when (weatherCode) {
        in 200..299 -> R.drawable.ic_thunderstorm
        in 300..399 -> R.drawable.ic_drizzle
        in 500..599 -> R.drawable.ic_rain
        in 600..699 -> R.drawable.ic_snow
        in 700..799 -> R.drawable.ic_atmosphere
        800 -> R.drawable.ic_clear
        in 801..804 -> R.drawable.ic_cloudy
        else -> R.drawable.ic_default_weather
    }
}

fun Double.format(digits: Int) = "%.${digits}f".format(this).toDouble()

fun checkPermissionStatus(
    context: Context,
    permission: String,
    isGranted: () -> Unit,
    showRational: () -> Unit,
    isNotGranted: () -> Unit,
) {
    when {
        ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED -> {
            isGranted()
        }
        ActivityCompat.shouldShowRequestPermissionRationale(
            context as WeatherActivity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) -> {
            showRational()
        }
        else -> {
            isNotGranted()
        }
    }
}

fun isLocationServiceEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return LocationManagerCompat.isLocationEnabled(locationManager)
}

fun enableLocation(context: Context){
    try {
        context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    } catch (exception: Exception) {
        Log.d("ERROR", "${exception.message}")
    }
}
