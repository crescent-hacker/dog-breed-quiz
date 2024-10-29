package com.airwallex.core.designsystem.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Int.toDp(context: Context): Dp = (this / context.resources.displayMetrics.density).dp

@Composable
fun Int.toDp(): Dp = (this / LocalContext.current.resources.displayMetrics.density).dp

fun Dp.toPx(context: Context): Float = value * context.resources.displayMetrics.density

@Composable
fun Dp.toPx(): Float = value * LocalContext.current.resources.displayMetrics.density

@Composable
fun rememberScreenHeight(): Dp {
    val screenHeight = LocalWindowSizeClass.current.heightWindowDpSize

    return remember { screenHeight }
}

@Composable
fun rememberScreenHeightPx(): Float {
    val screenHeight = LocalWindowSizeClass.current.heightWindowDpSize.toPx()

    return remember { screenHeight }
}

@Composable
fun rememberScreenWidth(): Dp {
    val screenWidth = LocalWindowSizeClass.current.widthWindowDpSize

    return remember { screenWidth }
}

@Composable
fun rememberScreenWidthPx(): Float {
    val screenWidth = LocalWindowSizeClass.current.widthWindowDpSize.toPx()

    return remember { screenWidth }
}

