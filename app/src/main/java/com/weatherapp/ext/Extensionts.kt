package com.weatherapp.ext

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.weatherapp.R
import com.weatherapp.WeatherActivity

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