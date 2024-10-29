package com.airwallex.core.designsystem.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.airwallex.core.designsystem.theme.LocalNightMode

fun Modifier.textBrush(brush: Brush) = this
    .graphicsLayer(alpha = 0.99f)
    .drawWithCache {
        onDrawWithContent {
            drawContent()
            drawRect(brush, blendMode = BlendMode.SrcAtop)
        }
    }

fun Modifier.textLinearGradientBrush(nightColorsStops: List<Pair<Float, Color>>, dayColorsStops: List<Pair<Float, Color>>) = composed { this
    .textBrush(
        brush = if (LocalNightMode.current)
            Brush.linearGradient(*nightColorsStops.toTypedArray())
        else
            Brush.linearGradient(*dayColorsStops.toTypedArray())
    ) }

@Composable
fun getDp(textUnit: TextUnit) = with(LocalDensity.current) {
    textUnit.toDp()
}

val Dp.textSp: TextUnit
    @Composable get() =  with(LocalDensity.current) {
        this@textSp.toSp()
    }
