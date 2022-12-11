package com.weatherapp.ui.utils

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.weatherapp.R

@Composable
fun ErrorDialog(@StringRes error: Int, clickedOk: () -> Unit) {
    Column {
        Dialog(onDismissRequest = {
            clickedOk()
        }) {
            Card(
                shape = RoundedCornerShape(5.dp), modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(error),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier
                            .padding(
                                top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp
                            )
                            .fillMaxWidth()
                    ) {

                        OutlinedButton(
                            onClick = {
                                clickedOk()
                            },
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Text(
                                text = stringResource(id = R.string.str_ok),
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}