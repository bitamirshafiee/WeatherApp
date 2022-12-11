package com.weatherapp

import android.Manifest
import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.weatherapp.repository.NetworkResult
import com.weatherapp.repository.model.LocationData
import com.weatherapp.repository.model.response.CurrentWeather
import com.weatherapp.repository.model.response.Weather
import com.weatherapp.repository.model.response.WeatherResponse
import com.weatherapp.repository.weather.WeatherRepository
import com.weatherapp.ui.theme.WeatherAppTheme
import com.weatherapp.ui.weatherdetails.WeatherDetails
import com.weatherapp.ui.weatherdetails.WeatherDetailsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CurrentWeatherTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    var mRuntimePermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(Manifest.permission.ACCESS_COARSE_LOCATION)

    lateinit var instrumentationContext: Context

    lateinit var viewModel: WeatherDetailsViewModel

    @Before
    fun initView() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context


        viewModel = WeatherDetailsViewModel(MockRepository())
        composeTestRule.setContent {
            WeatherAppTheme {
                WeatherDetails(viewModel = viewModel)
            }
        }
    }

    @Test
    fun checkGetWeatherAPITest() {
        viewModel.getCurrentWeather(LocationData(12.56, 34.6))
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("TAG")
        composeTestRule.onNodeWithText("Stockholm").assertExists()
    }

}

class MockRepository : WeatherRepository() {
    override suspend fun getWeather(locationData: LocationData): NetworkResult<WeatherResponse> {
        return NetworkResult.Success(
            WeatherResponse(
                timezone = "Stockholm", current = CurrentWeather(
                    temperature = 12.5,
                    feelsLike = 4.5,
                    weather = listOf(Weather(weatherCode = 800))
                )
            )
        )
    }
}