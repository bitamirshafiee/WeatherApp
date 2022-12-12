package com.weatherapp.ui.utils

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CircularProgress(modifier: Modifier, isVisible: Boolean) {
    if (isVisible)
        CircularProgressIndicator(
            modifier = modifier
        )
}