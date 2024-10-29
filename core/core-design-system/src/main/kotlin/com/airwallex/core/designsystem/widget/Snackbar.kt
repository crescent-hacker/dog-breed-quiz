package com.airwallex.core.designsystem.widget

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarData
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp
import com.airwallex.core.designsystem.theme.snackBarBackground
import com.airwallex.core.designsystem.theme.snackBarContent

@Composable
fun AirwallexSnackBar(
    snackBarData: SnackbarData,
    modifier: Modifier = Modifier
) {
    Snackbar(
        snackbarData = snackBarData,
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.snackBarBackground,
        contentColor = MaterialTheme.colors.snackBarContent,
    )
}

@Composable
fun SnackBarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .composed {
                modifier
            },
        hostState = hostState,
        snackbar = { AirwallexSnackBar(it) }
    )
}
