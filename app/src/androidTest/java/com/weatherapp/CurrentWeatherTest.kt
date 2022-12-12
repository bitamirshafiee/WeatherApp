package com.weatherapp

import android.Manifest
import android.content.Context
import android.nfc.FormatException
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
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.HttpException
import retrofit2.Response

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
    }

    @Test
    fun checkGetWeatherAPITest() {
        viewModel = WeatherDetailsViewModel(MockRepositorySuccess(0))
        composeTestRule.setContent {
            WeatherAppTheme {
                WeatherDetails(viewModel = viewModel)
            }
        }

        viewModel.getCurrentWeather(LocationData(12.56, 34.6))
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("TAG")
        composeTestRule.onNodeWithText("Stockholm").assertExists()
    }

    @Test
    fun checkGetWeatherAPIFail_UnAuthorize_Test() {
        viewModel = WeatherDetailsViewModel(MockRepositorySuccess(1))
        composeTestRule.setContent {
            WeatherAppTheme {
                WeatherDetails(viewModel = viewModel)
            }
        }

        viewModel.getCurrentWeather(LocationData(12.56, 34.6))
        composeTestRule.onNodeWithText("unAuthorized").assertExists()
    }

}

class MockRepositorySuccess(private val responseType: Int? = null) : WeatherRepository() {
    override suspend fun getWeather(locationData: LocationData): NetworkResult<WeatherResponse> {
        return when (responseType) {
            0 -> NetworkResult.Success(
                WeatherResponse(
                    timezone = "Stockholm", current = CurrentWeather(
                        temperature = 12.5,
                        feelsLike = 4.5,
                        weather = listOf(Weather(weatherCode = 800))
                    )
                )
            )
            1 -> NetworkResult.Failure(
                HttpException(
                    Response.error<ResponseBody>(
                        401,
                        ""
                            .toResponseBody("plain/text".toMediaType())
                    )
                )
            )
            2 -> NetworkResult.Failure(FormatException("WrongFormat"))
            else -> NetworkResult.Failure(Throwable())
        }

    }
}
