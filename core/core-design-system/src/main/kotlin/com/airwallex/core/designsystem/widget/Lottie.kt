package com.airwallex.core.designsystem.widget

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Lottie(
    scope: CoroutineScope = rememberCoroutineScope(),
    modifier: Modifier = Modifier,
    lottieModifier: Modifier = Modifier,
    @RawRes rawLottieRes: Int,
    onComplete: suspend CoroutineScope.() -> Unit = {},
    clipSpec: LottieClipSpec.Progress? = null,
    iterations: Int = LottieConstants.IterateForever,
    contentScale: ContentScale = ContentScale.Fit,
    color: Color? = null,
    isPlaying: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val composition = rememberLottieComposition(spec = LottieCompositionSpec.RawRes(rawLottieRes))
        val progress = if (clipSpec != null) {
            animateLottieCompositionAsState(
                isPlaying = isPlaying,
                composition = composition.value,
                clipSpec = clipSpec
            )
        } else {
            animateLottieCompositionAsState(
                isPlaying = isPlaying,
                composition = composition.value,
                iterations = iterations
            )
        }

        val endProgress = clipSpec?.max ?: 1f
        val dynamicProperties = if (color != null) rememberLottieDynamicProperties(
            rememberLottieDynamicProperty(
                keyPath = arrayOf("**"),
                property = LottieProperty.COLOR_FILTER,
                callback = { PorterDuffColorFilter(color.toArgb(), PorterDuff.Mode.SRC_ATOP) })
        ) else null

        LottieAnimation(
            contentScale = contentScale,
            modifier = lottieModifier,
            composition = composition.value,
            progress = { progress.value },
            dynamicProperties = dynamicProperties
        )

        if (composition.isComplete
            && progress.progress == endProgress
            && !progress.isPlaying
        ) {
            LaunchedEffect(Unit) {
                scope.launch {
                    onComplete()
                }
            }
        }
    }
}
