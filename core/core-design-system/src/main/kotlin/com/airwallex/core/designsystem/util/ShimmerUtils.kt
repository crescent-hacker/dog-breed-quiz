package com.airwallex.core.designsystem.util

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.placeholder
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.ShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import com.airwallex.core.basic.ext.runIf
import com.airwallex.core.designsystem.theme.customShapes
import com.airwallex.core.designsystem.theme.extendedColors

fun Modifier.iconShimmer(
    isVisible: Boolean,
    shape: Shape = MaterialTheme.customShapes.fiftyPercentAllCornersRounded,
    shimmerBounds: ShimmerBounds = ShimmerBounds.View

) = this.placeholderShimmer(isVisible = isVisible, shape = shape, shimmerBounds = shimmerBounds)

fun Modifier.textShimmer(
    isVisible: Boolean,
    shape: Shape = MaterialTheme.customShapes.fiftyPercentAllCornersRounded,
    shimmerBounds: ShimmerBounds = ShimmerBounds.View,
    maxHeight: Dp? = null,
    backgroundColor: Color = Color.Unspecified
) = this
    .runIf(isVisible) {
        this
            .runIf(backgroundColor != Color.Unspecified) {
                background(color = backgroundColor, shape = shape)
            }
            .runIf(maxHeight != null) {
                heightIn(max = maxHeight!!)
            }
    }
    .placeholderShimmer(isVisible = isVisible, shape = shape, shimmerBounds = shimmerBounds)

fun Modifier.buttonShimmer(
    isVisible: Boolean,
    shape: Shape = MaterialTheme.customShapes.fiftyPercentAllCornersRounded,
    shimmerBounds: ShimmerBounds = ShimmerBounds.View
) = this.placeholderShimmer(isVisible = isVisible, shape = shape, shimmerBounds = shimmerBounds).fillMaxHeight(1f)

fun Modifier.cardShimmer(
    isVisible: Boolean,
    shape: Shape = MaterialTheme.customShapes.smallAllCornersRounded,
    color: Color = Color.Unspecified,
    shimmerBounds: ShimmerBounds = ShimmerBounds.View
) = this.placeholderShimmer(isVisible = isVisible, shape = shape, color = color, shimmerBounds = shimmerBounds)

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.rowShimmer(
    isVisible: Boolean,
    /*@FloatRange(from = 0.0, to = 1.0)*/ fraction: Float = 1f,
    shape: Shape = MaterialTheme.customShapes.fiftyPercentAllCornersRounded,
    shimmerBounds: ShimmerBounds = ShimmerBounds.View,
    maxHeight: Dp? = null,
    backgroundColor: Color = Color.Unspecified
): Modifier = this
    .fillMaxWidth(if (isVisible) fraction else 1f)
    .runIf(isVisible) {
        this
            .runIf(backgroundColor != Color.Unspecified) {
                background(color = backgroundColor, shape = shape)
            }
            .runIf(maxHeight != null) {
                heightIn(max = maxHeight!!)
            }
    }
    .placeholderShimmer(isVisible = isVisible, shape = shape, shimmerBounds = shimmerBounds)

fun Modifier.placeholderShimmer(
    isVisible: Boolean,
    shape: Shape = MaterialTheme.customShapes.fiftyPercentAllCornersRounded,
    color: Color = Color.Unspecified,
    shimmerBounds: ShimmerBounds = ShimmerBounds.View
) =
    this
        .runIf(isVisible) { customShimmer(shimmerBounds) }
        .composed {
            placeholder(
                visible = isVisible,
                color = if (color == Color.Unspecified) MaterialTheme.extendedColors.placeholder else color,
                shape = shape,
                placeholderFadeTransitionSpec = { tween() },
                contentFadeTransitionSpec = { tween() }
            )
        }

/**************************************************
 *                  helpers                       *
 **************************************************/

fun Modifier.customShimmer(shimmerBounds: ShimmerBounds = ShimmerBounds.View): Modifier =
    this.composed {
        val shimmer = rememberShimmer(
            shimmerBounds = shimmerBounds,
            theme = customShimmerTheme,
        )
        shimmer(customShimmer = shimmer)
    }

private val customShimmerTheme: ShimmerTheme = ShimmerTheme(
    animationSpec = infiniteRepeatable(
        animation = tween(
            800,
            easing = LinearEasing,
            delayMillis = 500,
        ),
        repeatMode = RepeatMode.Restart,
    ),
    blendMode = BlendMode.DstIn,
    rotation = 15.0f,
    shaderColors = listOf(
        Color.Unspecified.copy(alpha = 0.25f),
        Color.Unspecified.copy(alpha = 1.00f),
        Color.Unspecified.copy(alpha = 0.25f),
    ),
    shaderColorStops = listOf(
        0.0f,
        0.5f,
        1.0f,
    ),
    shimmerWidth = 400.dp,
)


