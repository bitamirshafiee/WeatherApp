package com.weatherapp.ui.locationcheck

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.weatherapp.R
import com.weatherapp.ext.enableLocation
import com.weatherapp.ext.isLocationServiceEnabled

@Composable
fun LocationCheck(navigateToWeatherDetails: () -> Unit) {

    val lifecycleOwner: LifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val context = LocalContext.current

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->

            val isLocationEnabled = isLocationServiceEnabled(context = context)

            if (event == Lifecycle.Event.ON_CREATE && isLocationEnabled) {
                navigateToWeatherDetails()
            }

            if (event == Lifecycle.Event.ON_RESUME && isLocationEnabled) {
                navigateToWeatherDetails()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            painter = painterResource(id = R.drawable.ic_location_off),
            contentDescription = stringResource(id = R.string.str_location_permission_need)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
        ) {
            Text(
                text = stringResource(id = R.string.str_open_location_setting),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp)
            )

            Button(
                modifier = Modifier.padding(top = 16.dp),
                onClick = { enableLocation(context = context) }) {
                Text(text = stringResource(id = R.string.str_click_to_enable))
            }
        }
    }
}

@Preview
@Composable
fun LocationCheckPreview() {
    LocationCheck {}
}