package com.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.weatherapp.ui.WeatherAppNavHost
import com.weatherapp.ui.theme.WeatherAppTheme
import com.weatherapp.ui.weatherdetails.WeatherDetailsViewModel
import javax.inject.Inject

class WeatherActivity : ComponentActivity() {

//    @Inject
//    lateinit var viewModel: WeatherDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherAppNavHost()
                }
            }
        }
    }
}
