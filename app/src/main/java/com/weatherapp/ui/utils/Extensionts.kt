package com.weatherapp.ui.utils

import com.weatherapp.R

fun chooseDrawableAccordingToWeatherState(weatherCode : Int) : Int{

    return when(weatherCode){
        else -> R.drawable.ic_default_weather
    }
}