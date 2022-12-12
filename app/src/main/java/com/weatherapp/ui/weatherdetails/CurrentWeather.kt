package com.weatherapp.ui.weatherdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.weatherapp.R
import com.weatherapp.ext.chooseDrawableAccordingToWeatherState
import com.weatherapp.repository.model.response.Weather
import com.weatherapp.repository.model.response.WeatherResponse

@Composable
fun CurrentWeather(weatherResponse: WeatherResponse) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(vertical = 16.dp)
                    .size(75.dp), painter = painterResource(
                    id = chooseDrawableAccordingToWeatherState(
                        weatherCode = weatherResponse.current.weather.first().weatherCode
                    )
                ), contentDescription = stringResource(
                    id = R.string.str_weather_condition
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = weatherResponse.timezone,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .alignByBaseline(),
                )
                Text(
                    text = stringResource(
                        id = R.string.str_current_temperature, weatherResponse.current.temperature
                    ),
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .alignByBaseline()
                        .semantics {
                            contentDescription = "currentWeather"
                        }
                )
            }
            Text(
                text = stringResource(
                    id = R.string.str_feels_like, weatherResponse.current.feelsLike
                ),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .padding(all = 8.dp)
                    .wrapContentWidth(align = Alignment.End)
            )
        }
}

@Preview(showBackground = true)
@Composable
fun CurrentWeatherPreview() {
    CurrentWeather(
        WeatherResponse(
            timezone = "Stockholm",
            current = com.weatherapp.repository.model.response.CurrentWeather(
                temperature = 17.1, feelsLike = 11.1, weather = listOf(Weather(weatherCode = 800))
            )
        )
    )
}