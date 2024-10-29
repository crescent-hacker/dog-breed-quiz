package com.airwallex.core.designsystem.util

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.IntOffset

/****************************************************************
 *                   Visibility Transitions                     *
 ****************************************************************/

fun slideInUp(durationMillis: Int = 230) = slideInVertically(animationSpec = tween(durationMillis), initialOffsetY = { it / 2 }) + fadeIn(animationSpec = tween(durationMillis))
fun slideInDown(durationMillis: Int = 230) = slideInVertically(animationSpec = tween(durationMillis), initialOffsetY = { -it / 2 }) + fadeIn(animationSpec = tween(durationMillis))
fun slideOutUp(durationMillis: Int = 180) = slideOutVertically(animationSpec = tween(durationMillis), targetOffsetY = { -it / 2 }) + fadeOut(animationSpec = tween(durationMillis))
fun slideOutDown(durationMillis: Int = 180) = slideOutVertically(animationSpec = tween(durationMillis), targetOffsetY = { it / 2 }) + fadeOut(animationSpec = tween(durationMillis))
fun slideInLeft(durationMillis: Int = 230) = slideInHorizontally(animationSpec = tween(durationMillis), initialOffsetX = { -it / 2 }) + fadeIn(animationSpec = tween(durationMillis))
fun slideInRight(durationMillis: Int = 230) = slideInHorizontally(animationSpec = tween(durationMillis), initialOffsetX = { it / 2 }) + fadeIn(animationSpec = tween(durationMillis))
fun slideOutLeft(durationMillis: Int = 230) = slideOutHorizontally(animationSpec = tween(durationMillis), targetOffsetX = { -it / 2 }) + fadeOut(animationSpec = tween(durationMillis))
fun slideOutRight(durationMillis: Int = 230) = slideOutHorizontally(animationSpec = tween(durationMillis), targetOffsetX = { it / 2 }) + fadeOut(animationSpec = tween(durationMillis))

/****************************************************************
 *                              Easing                          *
 ****************************************************************/

/**
 * Slow in -> Accelerate -> Slow out
 */
val SlowOutSlowInEasing: Easing = CubicBezierEasing(0.42f, 0.0f, 0.58f, 1.0f)

/****************************************************************
 *                              Misc                            *
 ****************************************************************/
fun Modifier.shake(enabled: Boolean) = composed(
    factory = {
        val scale by animateFloatAsState(
            targetValue = if (enabled) .9f else 1f,
            animationSpec = repeatable(
                iterations = 5,
                animation = tween(durationMillis = 50, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        Modifier.graphicsLayer {
            scaleX = if (enabled) scale else 1f
            scaleY = if (enabled) scale else 1f
        }
    },
    inspectorInfo = debugInspectorInfo {
        name = "shake"
        properties["enabled"] = enabled
    }
)
