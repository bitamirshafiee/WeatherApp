package com.weatherapp.repository

import android.Manifest
import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.weatherapp.repository.model.LocationData
import com.weatherapp.ui.theme.WeatherAppTheme
import com.weatherapp.ui.weatherdetails.WeatherDetails
import com.weatherapp.ui.weatherdetails.WeatherDetailsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CurrentWeatherApiTest {

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
        viewModel = WeatherDetailsViewModel(MockCurrentWeatherRepository(0))
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
        viewModel = WeatherDetailsViewModel(MockCurrentWeatherRepository(1))
        composeTestRule.setContent {
            WeatherAppTheme {
                WeatherDetails(viewModel = viewModel)
            }
        }

        viewModel.getCurrentWeather(LocationData(12.56, 34.6))
        composeTestRule.onAllNodes(isRoot()).printToLog("TAG")
        composeTestRule.onNodeWithText("unAuthorized").assertExists()
    }

    @Test
    fun checkGetWeatherAPIFail_FormatException_Test() {
        viewModel = WeatherDetailsViewModel(MockCurrentWeatherRepository(2))
        composeTestRule.setContent {
            WeatherAppTheme {
                WeatherDetails(viewModel = viewModel)
            }
        }

        viewModel.getCurrentWeather(LocationData(12.56, 34.6))
        composeTestRule.onAllNodes(isRoot()).printToLog("TAG")
        composeTestRule.onNodeWithText("Error in format").assertExists()
    }

    @Test
    fun checkGetWeatherAPIFail_EOFException_Test() {
        viewModel = WeatherDetailsViewModel(MockCurrentWeatherRepository(3))
        composeTestRule.setContent {
            WeatherAppTheme {
                WeatherDetails(viewModel = viewModel)
            }
        }

        viewModel.getCurrentWeather(LocationData(12.56, 34.6))
        composeTestRule.onAllNodes(isRoot()).printToLog("TAG")
        composeTestRule.onNodeWithText("Error in data conversion").assertExists()
    }

    @Test
    fun checkGetWeatherAPIFail_IOException_Test() {
        viewModel = WeatherDetailsViewModel(MockCurrentWeatherRepository(4))
        composeTestRule.setContent {
            WeatherAppTheme {
                WeatherDetails(viewModel = viewModel)
            }
        }

        viewModel.getCurrentWeather(LocationData(12.56, 34.6))
        composeTestRule.onAllNodes(isRoot()).printToLog("TAG")
        composeTestRule.onNodeWithText("No Internet").assertExists()
    }

    @Test
    fun checkGetWeatherAPIFail_JsonSyntaxException_Test() {
        viewModel = WeatherDetailsViewModel(MockCurrentWeatherRepository(5))
        composeTestRule.setContent {
            WeatherAppTheme {
                WeatherDetails(viewModel = viewModel)
            }
        }

        viewModel.getCurrentWeather(LocationData(12.56, 34.6))
        composeTestRule.onAllNodes(isRoot()).printToLog("TAG")
        composeTestRule.onNodeWithText("Error in data conversion").assertExists()
    }

    @Test
    fun checkGetWeatherAPIFail_UNDEFINE_Test() {
        viewModel = WeatherDetailsViewModel(MockCurrentWeatherRepository(10))
        composeTestRule.setContent {
            WeatherAppTheme {
                WeatherDetails(viewModel = viewModel)
            }
        }

        viewModel.getCurrentWeather(LocationData(12.56, 34.6))
        composeTestRule.onAllNodes(isRoot()).printToLog("TAG")
        composeTestRule.onNodeWithText("Error happened").assertExists()
    }

}
