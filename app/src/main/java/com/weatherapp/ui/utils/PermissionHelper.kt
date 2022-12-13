package com.weatherapp.ui.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.weatherapp.R

@ExperimentalPermissionsApi
@Composable
fun PermissionHelper(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    permission: String,
    permissionGranted: () -> Unit
) {
    val permissionState = rememberPermissionState(permission = permission)

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START && !permissionState.status.isGranted) {
                permissionState.launchPermissionRequest()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    when {
        permissionState.status.isGranted -> {
            permissionGranted()
        }
        else -> {
            val rationalAccordingToSituation = if (permissionState.status.shouldShowRationale) {
                R.string.str_permission_denied_before
            } else {
                R.string.str_first_time_or_not_ask_again
            }
            Button(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .wrapContentHeight(Alignment.CenterVertically)
                .padding(start = 8.dp, top = 8.dp, end = 8.dp),
                onClick = {
                    permissionState.launchPermissionRequest()
                }) {
                Text(
                    text = stringResource(id = rationalAccordingToSituation),
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(all = 8.dp)
                )
            }
        }
    }
}