package com.weatherapp.ui.locationcheck

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.weatherapp.R
import com.weatherapp.ext.enableLocation
import com.weatherapp.ext.isLocationServiceEnabled

@Composable
fun LocationCheck(navigateToWeatherDetails: () -> Unit) {

    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME && isLocationServiceEnabled(context = context)) {
                navigateToWeatherDetails()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    Text(text = stringResource(id = R.string.str_open_location_setting),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .wrapContentHeight(Alignment.CenterVertically)
            .padding(start = 8.dp, top = 8.dp, end = 8.dp)
            .clickable {
                enableLocation(context = context)
            })
}