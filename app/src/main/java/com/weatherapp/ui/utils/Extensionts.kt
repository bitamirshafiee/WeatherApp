package com.weatherapp.ui.utils

import com.weatherapp.R

fun chooseDrawableAccordingToWeatherState(weatherCode : Int) : Int{

    return when(weatherCode){
        else -> R.drawable.ic_question_mark_default
    }
}