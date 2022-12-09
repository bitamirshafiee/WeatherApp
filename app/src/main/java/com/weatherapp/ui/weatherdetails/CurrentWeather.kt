package com.weatherapp.ui.weatherdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
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
            .padding(all = 16.dp)
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(vertical = 16.dp)
                    .size(75.dp),
                painter = painterResource(id = chooseDrawableAccordingToWeatherState(weatherCode = 0)),
                contentDescription = stringResource(
                    id = R.string.str_weather_condition
                )
            )

            Text(
                text ="city",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(all = 8.dp),
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CurrentWeatherPreview() {
    CurrentWeather()
}