package com.airwallex.core.designsystem.util

import android.app.Activity
import android.view.View
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airwallex.core.basic.model.AirwallexException
import com.airwallex.core.designsystem.R
import com.airwallex.core.designsystem.widget.SystemBarPaddings
import com.airwallex.core.util.AppLogger
import com.airwallex.core.util.nonFatalError

fun Activity.saveSystemBarPaddings(paddings: SystemBarPaddings) {
    val rootView = findViewById<View>(android.R.id.content)
    rootView.setTag(R.id.com_airwallex_core_designsystem_system_bars_paddings, paddings)
}

fun Activity.getSystemBarPaddings(): SystemBarPaddings {
    val rootView = findViewById<View>(android.R.id.content)
    return rootView.getTag(R.id.com_airwallex_core_designsystem_system_bars_paddings) as? SystemBarPaddings
        ?: run {
            AppLogger.nonFatalError(SystemBarPaddingsTagNotFoundException)
            SystemBarPaddings(0.dp, 0.dp)
        }
}

@Composable
fun getImeHeight(): Dp {
    val imePaddings = WindowInsets.ime
    val topPaddingDp = imePaddings.asPaddingValues().calculateTopPadding()
    val bottomPaddingDp = imePaddings.asPaddingValues().calculateBottomPadding()
    return (topPaddingDp + bottomPaddingDp)
}

@Composable
fun rememberSystemBarPaddings(): State<SystemBarPaddings> {
    val systemBars = WindowInsets.systemBars
    val topPaddingDp = systemBars.asPaddingValues().calculateTopPadding()
    val bottomPaddingDp = systemBars.asPaddingValues().calculateBottomPadding()

    return rememberUpdatedState(
        newValue = SystemBarPaddings(
            statusBarPadding = topPaddingDp,
            navigationBarPadding = bottomPaddingDp
        )
    )
}

object SystemBarPaddingsTagNotFoundException : AirwallexException("System paddings tag is not found in activity.")
