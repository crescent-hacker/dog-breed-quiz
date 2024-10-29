package com.airwallex.feature.shared.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
import com.airwallex.core.basic.ext.runIf
import com.airwallex.core.designsystem.theme.BaseAppTheme
import com.airwallex.core.designsystem.theme.DarkAppTheme
import com.airwallex.core.designsystem.theme.LightAppTheme
import com.airwallex.core.designsystem.theme.AirwallexDarkColorPalette
import com.airwallex.core.designsystem.theme.AirwallexLightColorPalette
import com.airwallex.core.designsystem.util.id
import com.airwallex.core.designsystem.util.setupBottomNavBarColor
import com.airwallex.core.designsystem.util.setupStatusBarColor
import com.airwallex.core.designsystem.util.setupSystemBarColor
import com.airwallex.core.designsystem.util.topAppBarPadding
import com.airwallex.feature.shared.di.LocalAnalyticsPage
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * @param isImmersiveFullScreen screen will enter immersive full screen; [showUnderSystemBar] will be ignored when [isImmersiveFullScreen] is true
 * @param showUnderSystemBar screen will show under system bar, screen need to handle the system bar padding manually in this case;
 */
data class ScreenProperties(
    val trackingPage: String? = null,
    val trackingReference: String? = null,
    val systemBarBackgroundWhenEnter: @Composable () -> Color = @Composable { MaterialTheme.colors.background },
    val statusBarBackgroundWhenEnter: @Composable () -> Color = systemBarBackgroundWhenEnter,
    val navBarBackgroundWhenEnter: @Composable () -> Color = systemBarBackgroundWhenEnter,
    val enterDelay: Long = 230L,
    val isImmersiveFullScreen: Boolean = false,
    val showUnderSystemBar: Boolean = false,
    val showUnderAppBar: Boolean = false,
    val backgroundColor: @Composable () -> Color = @Composable { MaterialTheme.colors.background },
)

/**
 * Force to use dark theme for screens wrapped inside this container
 */
@Composable
fun DarkThemeScreenContainer(
    modifier: Modifier = Modifier,
    screenProperties: ScreenProperties = ScreenProperties(),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    backHandler: @Composable (() -> Unit)? = null,
    topAppBar: @Composable (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    backHandler?.invoke()

    DarkAppTheme {
        setupScreenSystemBar(
            screenProperties.copy(systemBarBackgroundWhenEnter = {
                AirwallexDarkColorPalette.background
            })
        )

        ScreenScaffold(
            modifier = modifier,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            backgroundColor = screenProperties.backgroundColor(),
            showUnderSystemBar = screenProperties.showUnderSystemBar,
            showUnderAppBar = screenProperties.showUnderAppBar,
            topAppBar = topAppBar,
            content = content,
            trackingPage = screenProperties.trackingPage,
            trackingReference = screenProperties.trackingReference
        )

    }
}

/**
 * Force to use Light theme for screens wrapped inside this container
 */
@Composable
fun LightThemeScreenContainer(
    modifier: Modifier = Modifier,
    screenProperties: ScreenProperties = ScreenProperties(),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    backHandler: @Composable (() -> Unit)? = null,
    topAppBar: @Composable (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    backHandler?.invoke()

    LightAppTheme {
        setupScreenSystemBar(
            screenProperties.copy(systemBarBackgroundWhenEnter = { AirwallexLightColorPalette.background })
        )

        ScreenScaffold(
            modifier = modifier,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            backgroundColor = screenProperties.backgroundColor(),
            showUnderSystemBar = screenProperties.showUnderSystemBar,
            showUnderAppBar = screenProperties.showUnderAppBar,
            topAppBar = topAppBar,
            content = content,
            trackingPage = screenProperties.trackingPage,
            trackingReference = screenProperties.trackingReference
        )
    }
}

/**
 * Use light/dark theme in app settings for screens wrapped inside this container
 */
@Composable
fun ThemeScreenContainer(
    modifier: Modifier = Modifier,
    screenProperties: ScreenProperties = ScreenProperties(),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    backHandler: @Composable (() -> Unit)? = null,
    topAppBar: @Composable (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val rememberedTopAppBar = remember { topAppBar }
    val rememberedContent = remember { content }
    val rememberedBackHandler = remember { backHandler }

    rememberedBackHandler?.invoke()

    BaseAppTheme {
        setupScreenSystemBar(screenProperties)

        ScreenScaffold(
            modifier = modifier,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            backgroundColor = screenProperties.backgroundColor(),
            showUnderSystemBar = screenProperties.showUnderSystemBar,
            showUnderAppBar = screenProperties.showUnderAppBar,
            topAppBar = rememberedTopAppBar,
            content = rememberedContent,
            trackingPage = screenProperties.trackingPage,
            trackingReference = screenProperties.trackingReference
        )
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun ScreenScaffold(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    backgroundColor: Color = MaterialTheme.colors.background,
    showUnderSystemBar: Boolean,
    showUnderAppBar: Boolean,
    topAppBar: @Composable() (() -> Unit)? = null,
    content: @Composable() (ColumnScope.() -> Unit),
    trackingPage: String? = null,
    trackingReference: String? = null
) {
    Scaffold(
        modifier = Modifier
            .id(trackingPage?.let { "screen-${it}" })
            .fillMaxSize(),
        backgroundColor = backgroundColor,
    ) { padding ->
        Box(
            modifier = modifier
                .runIf(!showUnderSystemBar) {
                    systemBarsPadding()
                }
                .padding(padding)
                .fillMaxSize()
        ) {
            topAppBar?.invoke()

            Column(
                verticalArrangement = verticalArrangement,
                horizontalAlignment = horizontalAlignment,
                modifier = Modifier
                    .fillMaxSize()
                    .runIf(topAppBar != null && !showUnderAppBar) {
                        topAppBarPadding()
                    }
            ) {
                trackingPage?.let {
                    CompositionLocalProvider(LocalAnalyticsPage provides trackingPage) {
                        content()
                    }
                } ?: content()
            }
        }
    }
}

@SuppressLint("ComposableNaming")
@Composable
private fun setupScreenSystemBar(screenProperties: ScreenProperties) {
    val systemUiController = rememberSystemUiController()

    if (screenProperties.isImmersiveFullScreen) {
        systemUiController.systemBarsBehavior = BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        systemUiController.isSystemBarsVisible = false
    } else {
        systemUiController.isSystemBarsVisible = true

        val statusBarColor = screenProperties.statusBarBackgroundWhenEnter.invoke()
        val navBarColor = screenProperties.navBarBackgroundWhenEnter.invoke()
        val systemBarColor = screenProperties.systemBarBackgroundWhenEnter.invoke()

        if (statusBarColor == systemBarColor && navBarColor == systemBarColor) {
            setupSystemBarColor(
                systemUiController = systemUiController,
                systemBarBackgroundWhenEnter = systemBarColor
                    .takeIf { !screenProperties.showUnderSystemBar }
                    ?: Color.Transparent,
                enterDelay = screenProperties.enterDelay
            )
        } else {
            setupStatusBarColor(
                systemUiController = systemUiController,
                statusBarBackgroundWhenEnter = statusBarColor
                    .takeIf { !screenProperties.showUnderSystemBar }
                    ?: Color.Transparent,
                enterDelay = screenProperties.enterDelay
            )

            setupBottomNavBarColor(
                systemUiController = systemUiController,
                navBarBackgroundWhenEnter = navBarColor
                    .takeIf { !screenProperties.showUnderSystemBar }
                    ?: Color.Transparent,
                enterDelay = screenProperties.enterDelay
            )
        }
    }
}
