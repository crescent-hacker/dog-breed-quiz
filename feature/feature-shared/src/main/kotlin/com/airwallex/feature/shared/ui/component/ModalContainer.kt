package com.airwallex.feature.shared.ui.component

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import com.airwallex.feature.shared.di.LocalAppEventBus
import com.airwallex.feature.shared.ui.base.AbstractActivity
import com.airwallex.core.basic.ext.asType
import com.airwallex.core.designsystem.theme.BaseAppTheme
import com.airwallex.core.designsystem.theme.Dimens
import com.airwallex.core.designsystem.util.fixedNavigationBarPadding
import com.airwallex.core.designsystem.util.isSmallHeightPhoneScreen
import com.airwallex.core.designsystem.util.setupModalSystemBarColor
import com.airwallex.core.designsystem.widget.LocalSheetBackgroundColor
import com.airwallex.core.designsystem.widget.OnCloseModal
import com.airwallex.core.designsystem.widget.showAsFullScreenModalSheet
import com.airwallex.core.designsystem.widget.showAsModalBottomSheet

@Composable
private fun ThemeModalContainer(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Bottom,
    content: @Composable ColumnScope.() -> Unit
) {
    BaseAppTheme {
        Column(
            content = content,
            verticalArrangement = verticalArrangement,
            modifier = modifier
                .padding(
                    top = Dimens.Global.LargePadding.get(),
                    start = Dimens.Global.MediumPadding.get(),
                    end = Dimens.Global.MediumPadding.get(),
                    bottom = if (isSmallHeightPhoneScreen()) Dimens.Global.MediumLargePadding.get() else Dimens.Global.MediumPadding.get(),
                )
                .fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
fun ComponentActivity.showAsModalBottomSheet(
    onModalClosed: () -> Unit = {},
    content: @Composable (OnCloseModal) -> Unit
) = with(this.asType<AbstractActivity>()) {
    showAsModalBottomSheet(
        onModalClosed = onModalClosed,
        content = { modalBottomSheetState ->
            CompositionLocalProvider(
                LocalAppEventBus provides appEventBus,
            ) {
                ThemeModalContainer(
                    modifier = Modifier.fixedNavigationBarPadding()
                ) {
                    val coroutineScope = rememberCoroutineScope()
                    val themeSheetBackgroundColor = LocalSheetBackgroundColor.current
                    val onCloseModal = OnCloseModal {
                        // Action passed for clicking close button in the content
                        coroutineScope.launch {
                            modalBottomSheetState.hide() // will trigger the LaunchedEffect
                        }
                    }

                    setupModalSystemBarColor(
                        systemBarBackgroundWhenEnter = themeSheetBackgroundColor
                    )
                    content.invoke(onCloseModal)
                }
            }
        }
    )
}

// don't use this
@OptIn(ExperimentalMaterialApi::class)
fun ComponentActivity.showAsFullScreenModalSheet(
    onModalClosed: () -> Unit = {},
    content: @Composable (OnCloseModal) -> Unit
) = with(this.asType<AbstractActivity>()) {
    showAsFullScreenModalSheet (
        onModalClosed = onModalClosed,
        content = { modalBottomSheetState ->
            CompositionLocalProvider(
                LocalAppEventBus provides appEventBus,
            ) {
                ThemeModalContainer(
                    modifier = Modifier.fixedNavigationBarPadding()
                ) {
                    val coroutineScope = rememberCoroutineScope()
                    val themeSheetBackgroundColor = LocalSheetBackgroundColor.current
                    val onCloseModal = OnCloseModal {
                        // Action passed for clicking close button in the content
                        coroutineScope.launch {
                            modalBottomSheetState.hide() // will trigger the LaunchedEffect
                        }
                    }

                    setupModalSystemBarColor(
                        systemBarBackgroundWhenEnter = themeSheetBackgroundColor,
                    )
                    content.invoke(onCloseModal)
                }
            }
        }
    )
}
