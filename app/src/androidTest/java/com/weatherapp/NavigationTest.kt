package com.weatherapp

import android.Manifest
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import com.weatherapp.ui.WeatherAppNavHost
import com.weatherapp.ui.theme.WeatherAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    var mRuntimePermissionRule: GrantPermissionRule = GrantPermissionRule
        .grant(Manifest.permission.ACCESS_COARSE_LOCATION)

    @Before
    fun initView() {
        composeTestRule.setContent {
            WeatherAppTheme {
                WeatherAppNavHost()
            }
        }
    }

    @Test
    fun navigateToWeatherDetailsPageTest(){
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("TAG")
        composeTestRule.onNodeWithTag("currentWeatherPage").assertExists()
    }
}