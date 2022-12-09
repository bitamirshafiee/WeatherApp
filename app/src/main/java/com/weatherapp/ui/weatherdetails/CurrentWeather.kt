package com.weatherapp.ui.weatherdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.weatherapp.R
import com.weatherapp.ui.utils.chooseDrawableAccordingToWeatherState

@Composable
fun CurrentWeather() {

    Card(
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Image(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .size(75.dp),
            painter = painterResource(id = chooseDrawableAccordingToWeatherState(0)),
            contentDescription = stringResource(
                id = R.string.str_weather_condition
            )
        )

    }
}

@Preview(showBackground = true)
@Composable
fun CurrentWeatherPreview() {
    CurrentWeather()
}