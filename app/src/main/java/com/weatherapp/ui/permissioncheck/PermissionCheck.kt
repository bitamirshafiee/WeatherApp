package com.weatherapp.ui.permissioncheck

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.weatherapp.R
import com.weatherapp.ext.checkPermissionStatus

@Composable
fun PermissionCheck(navigateToCheckIfLocationIsEnabled: () -> Unit) {

    val context = LocalContext.current
    var isShowPermissionDenied by remember { mutableStateOf(false) }
    var permissionStatusInformation by remember { mutableStateOf(context.getString(R.string.str_feature_is_unavailable)) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            navigateToCheckIfLocationIsEnabled()
        } else {
            isShowPermissionDenied = true
            permissionStatusInformation = context.getString(R.string.str_feature_is_unavailable)
        }
    }
    LaunchedEffect(Unit) {
        checkPermissionStatus(context = context,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            isGranted = {
                navigateToCheckIfLocationIsEnabled()
            },
            showRational = {
                permissionStatusInformation = context.getString(R.string.str_why_we_need_permission)
                launcher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            },
            isNotGranted = {
                isShowPermissionDenied = true
                launcher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            })
    }

    if (isShowPermissionDenied)
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                painter = painterResource(id = R.drawable.ic_location_disabled),
                contentDescription = stringResource(id = R.string.str_location_permission_need)
            )
            Text(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(all = 16.dp)
                    .weight(1f),
                style = MaterialTheme.typography.titleLarge,
                text = permissionStatusInformation
            )
        }
}

@Preview
@Composable
fun PermissionCheckPreview() {
    PermissionCheck {}
}