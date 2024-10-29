package com.airwallex.core.designsystem.widget

import android.app.Activity
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.airwallex.core.designsystem.theme.DayNightColor
import com.airwallex.core.designsystem.theme.LocalAppThemeMode
import com.airwallex.core.designsystem.theme.LocalNightMode
import com.airwallex.core.designsystem.theme.AirwallexLightColorPalette
import com.airwallex.core.designsystem.theme.customShapes
import com.airwallex.core.designsystem.theme.getCurrentAppCompatThemeMode
import com.airwallex.core.designsystem.theme.isDisplayNightMode
import com.airwallex.core.designsystem.util.LocalLatestLifecycleEvent
import com.airwallex.core.designsystem.util.rememberLatestLifecycleEvent
import com.airwallex.core.util.AppLogger

// Extension for Activity
@OptIn(ExperimentalMaterialApi::class)
fun Activity.showAsModalBottomSheet(
    sheetBackgroundColor: DayNightColor = DayNightColor(light = AirwallexLightColorPalette.background),
    onModalClosed: () -> Unit = {},
    content: @Composable (ModalBottomSheetState) -> Unit
) {
    val rootViewGroup = this.findViewById(android.R.id.content) as ViewGroup

    val modalView = ComposeView(this).apply {
        setContent {
            ModalBottomSheetController(
                sheetBackgroundColor = sheetBackgroundColor,
            ) {
                ModalBottomSheetWrapper(
                    parent = rootViewGroup,
                    composeView = this,
                    onModalClosed = onModalClosed,
                    content = { modalBottomSheetState ->
                        content.invoke(modalBottomSheetState)
                        Spacer(modifier = Modifier.height(1.dp)) // provides minimum content if dynamic content is null on compose
                    }
                )
            }
        }
    }

    rootViewGroup.addView(modalView)
}


@Composable
private fun ModalBottomSheetController(
    sheetBackgroundColor: DayNightColor,
    content: @Composable () -> Unit
) {
    val currentAppThemeMode = getCurrentAppCompatThemeMode()
    val isDisplayNightMode = isDisplayNightMode(currentAppThemeMode)
    val selectedSheetBackgroundColor = if (isDisplayNightMode) sheetBackgroundColor.dark else sheetBackgroundColor.light

    val latestLifecycleEvent by rememberLatestLifecycleEvent()

    CompositionLocalProvider(
        LocalAppThemeMode provides currentAppThemeMode,
        LocalNightMode provides isDisplayNightMode,
        LocalSheetBackgroundColor provides selectedSheetBackgroundColor,
        LocalLatestLifecycleEvent provides latestLifecycleEvent,
        content = content
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ModalBottomSheetWrapper(
    parent: ViewGroup,
    composeView: ComposeView,
    onModalClosed: () -> Unit = {},
    content: @Composable (ModalBottomSheetState) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = {
            if (it == ModalBottomSheetValue.Hidden) {
                onModalClosed()
            }
            it != ModalBottomSheetValue.HalfExpanded
        }
    )
    var isSheetOpened by remember { mutableStateOf(false) }
    val themeSheetBackgroundColor = LocalSheetBackgroundColor.current

    ModalBottomSheetLayout(
        modifier = Modifier,
        sheetShape = MaterialTheme.customShapes.mediumTopCornersRounded,
        sheetState = modalBottomSheetState,
        sheetBackgroundColor = themeSheetBackgroundColor,
        sheetContent = {
            content.invoke(modalBottomSheetState)
        }
    ) {
        // the content of rest of the screen, due to we show the modal in another composeView on top of current activity,
        // we should leave this empty
    }

    BackHandler {
        coroutineScope.launch {
            modalBottomSheetState.hide() // will trigger the LaunchedEffect
        }
    }

    // Take action based on hidden state
    LaunchedEffect(modalBottomSheetState.currentValue) {
        when (modalBottomSheetState.currentValue) {
            ModalBottomSheetValue.Hidden -> {
                when {
                    isSheetOpened -> parent.removeView(composeView)
                    else -> {
                        isSheetOpened = true
                        modalBottomSheetState.show()
                    }
                }
            }

            else -> {
                AppLogger.debug("Bottom sheet ${modalBottomSheetState.currentValue} state")
            }
        }
    }
}

data class SystemBarPaddings(
    val statusBarPadding: Dp = 0.dp,
    val navigationBarPadding: Dp = 0.dp
) {
    fun hasValues() = statusBarPadding != 0.dp || navigationBarPadding != 0.dp
}

fun interface OnCloseModal {
    fun invoke()
}

val LocalSheetBackgroundColor = compositionLocalOf<Color> {
    error("SheetBackgroundColor is not provided.")
}
