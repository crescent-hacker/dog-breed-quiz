package com.airwallex.core.designsystem.util

import android.app.Activity
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.toComposeRect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.window.layout.WindowMetricsCalculator

/**
 * resolve window screen size
 *
 * @see [Support different screen sizes](https://developer.android.com/guide/topics/large-screens/support-different-screen-sizes#kotlin)
 */
data class WindowSizeClass(
    val widthWindowSizeClass: WindowType,
    val heightWindowSizeClass: WindowType,
    val widthWindowDpSize: Dp,
    val heightWindowDpSize: Dp
) {
    /**
     * Definition of window size that app cares
     *
     * COMPACT: 0dp ~ 600dp, includes phone(handset) size in small, med, large with portrait orientation
     * MEDIUM: width - 600dp ~ 840dp; height
     * EXPANDED: 840dp and above
     *
     * @note app only handles portrait mode in both phone and tablet
     */
    sealed class WindowType {
        object CompactHandsetPortraitExtraSmall : WindowType()
        object CompactHandsetPortraitSmall : WindowType()
        object CompactHandsetPortraitMedium : WindowType()
        object CompactHandsetPortraitLarge : WindowType()
        object CompactHandsetLandscapeSmall : WindowType()

        /**
         * includes sizes:
         * 1. handset landscape - med to large
         * 2. tablet portrait - small to large
         */
        object Medium : WindowType()
        object Expanded : WindowType()
    }
}

@Composable
fun isSmallWidthPhoneScreen() = LocalWindowSizeClass.current.widthWindowSizeClass == WindowSizeClass.WindowType.CompactHandsetPortraitSmall

@Composable
fun isMediumWidthPhoneScreen() = LocalWindowSizeClass.current.widthWindowSizeClass == WindowSizeClass.WindowType.CompactHandsetPortraitMedium

@Composable
fun isLargeWidthPhoneScreen() = LocalWindowSizeClass.current.widthWindowSizeClass == WindowSizeClass.WindowType.CompactHandsetPortraitLarge

@Composable
fun isExtraSmallHeightPhoneScreen() = LocalWindowSizeClass.current.heightWindowSizeClass == WindowSizeClass.WindowType.CompactHandsetPortraitExtraSmall

@Composable
fun isSmallHeightPhoneScreen() = LocalWindowSizeClass.current.heightWindowSizeClass == WindowSizeClass.WindowType.CompactHandsetPortraitSmall

@Composable
fun isMediumHeightPhoneScreen() = LocalWindowSizeClass.current.heightWindowSizeClass == WindowSizeClass.WindowType.CompactHandsetPortraitMedium

@Composable
fun isLargeHeightPhoneScreen() = LocalWindowSizeClass.current.heightWindowSizeClass == WindowSizeClass.WindowType.CompactHandsetPortraitLarge

fun Density.toDpSize(size: Size): DpSize = with(this) {
    DpSize(size.width.toDp(), size.height.toDp())
}

@Composable
fun Activity.getWindowSizePx(): Size {
    val configuration = LocalConfiguration.current
    val windowMetrics = WindowMetricsCalculator.getOrCreate()
        .computeCurrentWindowMetrics(activity = this)

    return windowMetrics.bounds.toComposeRect().size
}

@Composable
fun Activity.rememberWindowSizeClass(): WindowSizeClass {
    val configuration = LocalConfiguration.current
    val windowMetrics = remember(configuration) {
        WindowMetricsCalculator.getOrCreate()
            .computeCurrentWindowMetrics(activity = this)
    }

    val windowDpSize: DpSize = with(LocalDensity.current) {
        toDpSize(windowMetrics.bounds.toComposeRect().size)
    }

    val windowSizeClass = WindowSizeClass(
        widthWindowSizeClass = when {
            windowDpSize.width < 0.dp -> throw IllegalArgumentException("Dp value cannot be negative")
            windowDpSize.width < 360.dp -> WindowSizeClass.WindowType.CompactHandsetPortraitSmall
            windowDpSize.width < 400.dp -> WindowSizeClass.WindowType.CompactHandsetPortraitMedium
            windowDpSize.width < 600.dp -> resolveWindowTypeByOrientation(
                portrait = WindowSizeClass.WindowType.CompactHandsetPortraitLarge,
                landscape = WindowSizeClass.WindowType.CompactHandsetLandscapeSmall
            )
            windowDpSize.width < 840.dp -> WindowSizeClass.WindowType.Medium
            else -> WindowSizeClass.WindowType.Expanded
        },
        // TODO, it's just a rough classification for height, need to do more investigation to make it more precise
        heightWindowSizeClass = when {
            windowDpSize.height < 0.dp -> throw IllegalArgumentException("Dp value cannot be negative")
            windowDpSize.height < 600.dp -> WindowSizeClass.WindowType.CompactHandsetPortraitExtraSmall
            windowDpSize.height < 800.dp -> WindowSizeClass.WindowType.CompactHandsetPortraitSmall
            windowDpSize.height < 1000.dp -> WindowSizeClass.WindowType.CompactHandsetPortraitMedium
            windowDpSize.height < 1200.dp -> WindowSizeClass.WindowType.CompactHandsetPortraitLarge
            windowDpSize.height < 1400.dp -> WindowSizeClass.WindowType.Medium
            else -> WindowSizeClass.WindowType.Expanded
        },
        widthWindowDpSize = windowDpSize.width,
        heightWindowDpSize = windowDpSize.height
    )

    return remember { windowSizeClass }
}

val LocalWindowSizeClass = compositionLocalOf {
    WindowSizeClass(
        widthWindowSizeClass = WindowSizeClass.WindowType.CompactHandsetPortraitMedium,
        heightWindowSizeClass = WindowSizeClass.WindowType.CompactHandsetPortraitMedium,
        widthWindowDpSize = 0.dp,
        heightWindowDpSize = 0.dp
    )
}

@Composable
private fun resolveWindowTypeByOrientation(portrait: WindowSizeClass.WindowType, landscape: WindowSizeClass.WindowType) =
    if (LocalConfiguration.current.orientation == ORIENTATION_PORTRAIT)
        portrait
    else
        landscape
