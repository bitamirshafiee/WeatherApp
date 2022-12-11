package com.ext

import com.weatherapp.R
import com.weatherapp.ext.chooseDrawableAccordingToWeatherState
import com.weatherapp.ext.format
import org.junit.Test

class Extensions {

    @Test
    fun doubleFormat() {
        val number = 12.1534567890
        val result = number.format(2)
        assert(result == 12.15)
    }

    @Test
    fun chooseDrawableAccordingToWeatherStateTest() {
        val result1 = chooseDrawableAccordingToWeatherState(205)
        assert(result1 == R.drawable.ic_thunderstorm)

        val result2 = chooseDrawableAccordingToWeatherState(350)
        assert(result2 == R.drawable.ic_drizzle)

        val result3 = chooseDrawableAccordingToWeatherState(540)
        assert(result3 == R.drawable.ic_rain)

        val result4 = chooseDrawableAccordingToWeatherState(678)
        assert(result4 == R.drawable.ic_snow)

        val result5 = chooseDrawableAccordingToWeatherState(740)
        assert(result5 == R.drawable.ic_atmosphere)

        val result6 = chooseDrawableAccordingToWeatherState(800)
        assert(result6 == R.drawable.ic_clear)

        val result7 = chooseDrawableAccordingToWeatherState(801)
        assert(result7 == R.drawable.ic_cloudy)

        val result8 = chooseDrawableAccordingToWeatherState(900)
        assert(result8 == R.drawable.ic_default_weather)
    }
}