package com.weatherapp.ext

import com.weatherapp.R
import java.math.BigDecimal

fun chooseDrawableAccordingToWeatherState(weatherCode: Int): Int {

    return when (weatherCode) {
        else -> R.drawable.ic_default_weather
    }
}
fun Double.format(digits: Int) = "%.${digits}f".format(this).toDouble()

fun roundDouble(number: Double, scale : Int) = BigDecimal(number).setScale(scale).toDouble()