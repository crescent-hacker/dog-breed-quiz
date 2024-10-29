package com.airwallex.core.designsystem.widget

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import com.airwallex.core.designsystem.R
import com.airwallex.core.designsystem.theme.iconTintQuaternary

@Composable
fun SpinnerLoader(
    modifier: Modifier = Modifier,
    lottieModifier: Modifier = Modifier,
    onComplete: suspend CoroutineScope.() -> Unit = {},
    color: Color = MaterialTheme.colors.iconTintQuaternary
) {
    Lottie(
        onComplete = onComplete,
        modifier = modifier,
        lottieModifier = lottieModifier,
        color = color,
        rawLottieRes = R.raw.spinner_small,
    )
}

@Composable
fun Loader(
    modifier: Modifier = Modifier,
    @RawRes rawLottieRes: Int,
    lottieModifier: Modifier = Modifier,
    onComplete: suspend CoroutineScope.() -> Unit = {},
) {
    Lottie(
        onComplete = onComplete,
        modifier = modifier,
        lottieModifier = lottieModifier,
        rawLottieRes = rawLottieRes,
    )
}
