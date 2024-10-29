package com.airwallex.core.designsystem.util

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airwallex.core.designsystem.theme.surfaceVariant

object LazyColumnUtils {
    enum class ItemPosition {
        FIRST, MIDDLE, LAST;

        companion object {
            fun List<*>.getItemPosition(index: Int) =
                    when {
                        this.isEmpty() -> FIRST
                        index == 0 -> FIRST
                        index == lastIndex -> LAST
                        else -> MIDDLE
                    }

        }
    }

    @Composable
    fun Modifier.drawBottomLine(
            width: Dp = 1.dp,
            color: Color = MaterialTheme.colors.surfaceVariant,
            verticalOffset: Float = 0f
    ): Modifier =
            drawWithContent {
                drawContent()
                val strokeWidth = width.value * density
                val y = size.height - strokeWidth / 2

                drawLine(
                        color,
                        Offset(0f, y - verticalOffset),
                        Offset(size.width, y - verticalOffset),
                        strokeWidth
                )
            }
}
