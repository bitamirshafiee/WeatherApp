package com.weatherapp.ext

import com.weatherapp.R
import java.math.BigDecimal

fun chooseDrawableAccordingToWeatherState(weatherCode: Int): Int {

    return when(weatherCode) {
        in 200..299 -> R.drawable.ic_thunderstorm
        in 300..399 -> R.drawable.ic_drizzle
        in 500..599 -> R.drawable.ic_rain
        in 600..699  -> R.drawable.ic_snow
        in 700..799 -> R.drawable.ic_atmosphere
        800 -> R.drawable.ic_clear
        in 801..804 -> R.drawable.ic_cloudy
        else -> R.drawable.ic_default_weather
    }
}

fun Double.format(digits: Int) = "%.${digits}f".format(this).toDouble()

fun roundDouble(number: Double, scale: Int) = BigDecimal(number).setScale(scale).toDouble()