package com.weatherapp.ui.permissioncheck

import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.weatherapp.ui.utils.PermissionHelper

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionCheck(navigateToCheckIfLocationIsEnabled: () -> Unit) {

    PermissionHelper(permission = android.Manifest.permission.ACCESS_COARSE_LOCATION) {

    }
}