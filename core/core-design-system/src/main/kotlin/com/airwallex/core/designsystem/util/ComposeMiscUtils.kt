package com.airwallex.core.designsystem.util

import android.content.Context
import android.graphics.BlurMaskFilter
import android.os.Build
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airwallex.core.designsystem.theme.AirwallexColors
import com.airwallex.core.designsystem.theme.LocalNightMode

@Composable
fun getActivity(): ComponentActivity = LocalContext.current as ComponentActivity

fun HapticFeedback.vibrate() = performHapticFeedback(HapticFeedbackType.LongPress)

fun Context.toast(text: String) =
    Toast.makeText(this, text, Toast.LENGTH_SHORT).apply {
        show()
    }

fun Context.longToast(text: String) =
    Toast.makeText(this, text, Toast.LENGTH_LONG).apply {
        show()
    }

@Composable
fun toast(text: String) {
    val ctx = LocalContext.current
    DisposableEffect(Unit) {
        val ref = ctx.toast(text)

        onDispose {
            ref.cancel()
        }
    }
}

@Composable
fun longToast(text: String) {
    val ctx = LocalContext.current
    DisposableEffect(Unit) {
        val ref = ctx.longToast(text)

        onDispose {
            ref.cancel()
        }
    }
}

fun Modifier.clickable(
    onClick: () -> Unit,
    showRipple: Boolean = true,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
): Modifier = composed {
    if (showRipple) {
        clickable(
            enabled = enabled,
            onClickLabel = onClickLabel,
            role = role,
            onClick = onClick
        )
    } else {
        val interactionSource = remember { MutableInteractionSource() }
        this.clickable(
            interactionSource = interactionSource,
            enabled = enabled,
            onClickLabel = onClickLabel,
            role = role,
            onClick = onClick,
            indication = null
        )
    }
}


internal interface MultipleEventsCutter {
    fun processEvent(event: () -> Unit)

    companion object
}

internal fun MultipleEventsCutter.Companion.get(): MultipleEventsCutter =
    MultipleEventsCutterImpl()

private class MultipleEventsCutterImpl : MultipleEventsCutter {
    private val now: Long
        get() = System.currentTimeMillis()

    private var lastEventTimeMs: Long = 0

    override fun processEvent(event: () -> Unit) {
        if (now - lastEventTimeMs >= 300L) {
            event.invoke()
        }
        lastEventTimeMs = now
    }
}

fun Modifier.clickableSingle(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    val multipleEventsCutter = remember { MultipleEventsCutter.get() }
    Modifier.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        onClick = { multipleEventsCutter.processEvent { onClick() } },
        role = role,
        indication = LocalIndication.current,
        interactionSource = remember { MutableInteractionSource() }
    )
}

fun Modifier.onLayoutCoordinatesPositioned(consumeLayoutCoordinates: (Rect) -> Unit) = this
    .onGloballyPositioned { layoutCoordinates ->
        val rect = layoutCoordinates.boundsInRoot()
        consumeLayoutCoordinates(rect)
    }

fun Modifier.spotShadow(
    color: Color = Color.Black,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    radiusX: Float = 33f,
    radiusY: Float = 33f,
) = then(
    drawBehind {
        drawIntoCanvas { canvas ->
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter = (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }
            frameworkPaint.color = color.toArgb()

            val topPixel = offsetY.toPx()
            val leftPixel = offsetX.toPx()
            val rightPixel = size.width + topPixel
            val bottomPixel = size.height + leftPixel

            canvas.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = radiusX,
                radiusY = radiusY,
                paint = paint,
            )
        }
    }
)

fun confirmHapticFeedback(view: View, hapticFeedback: HapticFeedback) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
    } else {
        // fallback to limited compose support
        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
    }
}

fun Modifier.advancedShadow(
    color: Color = Color.Black,
    alpha: Float = 1f,
    cornersRadius: Dp = 0.dp,
    shadowBlurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = drawBehind {

    val shadowColor = color.copy(alpha = alpha).toArgb()
    val transparentColor = color.copy(alpha = 0f).toArgb()

    drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowBlurRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            cornersRadius.toPx(),
            cornersRadius.toPx(),
            paint
        )
    }
}

fun Modifier.id(resourceId: String? = null): Modifier =
    resourceId?.let {
        this.testTag(resourceId)
    } ?: this
fun Modifier.visibility(visible: Boolean): Modifier {
    return layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        layout(placeable.width, placeable.height) {
            if (visible) {
                // place this item in the original position
                placeable.placeRelative(0, 0)
            }
        }
    }
}

fun Modifier.gradientBackground(
    baseColor: Color,
    alphas: List<Float> = listOf(0.4f, 0.5f, 0.8f)
): Modifier = composed { composed {
    val isDarkTheme = LocalNightMode.current
    if (!isDarkTheme) {
        background(
            brush = Brush.radialGradient(
                colors = alphas.map {
                    baseColor.copy(alpha = it)
                }
            )
        )
    } else this
} }
