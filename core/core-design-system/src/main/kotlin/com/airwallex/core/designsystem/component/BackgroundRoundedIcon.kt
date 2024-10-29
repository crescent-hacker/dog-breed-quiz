package com.airwallex.core.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airwallex.core.designsystem.theme.customShapes
import com.airwallex.core.designsystem.theme.iconBackgroundPrimary
import com.airwallex.core.designsystem.theme.iconBackgroundSecondary
import com.airwallex.core.designsystem.theme.iconBackgroundTertiary
import com.airwallex.core.designsystem.theme.iconTintPrimary
import com.airwallex.core.designsystem.theme.iconTintSecondary
import com.airwallex.core.designsystem.theme.iconTintTertiary
import com.airwallex.core.designsystem.component.BackgroundRoundedIconSizeType.LARGE
import com.airwallex.core.designsystem.component.BackgroundRoundedIconSizeType.NORMAL
import com.airwallex.core.designsystem.component.BackgroundRoundedIconTintMode.CUSTOM_TINT
import com.airwallex.core.designsystem.component.BackgroundRoundedIconTintMode.NO_TINT

enum class BackgroundRoundedIconTintMode {
    NO_TINT,
    CUSTOM_TINT
}

enum class BackgroundRoundedIconSizeType {
    NORMAL, LARGE
}

@Composable
fun PrimaryBackgroundRoundedIcon(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    backgroundTint: Color = MaterialTheme.colors.iconBackgroundPrimary,
    tintMode: BackgroundRoundedIconTintMode = CUSTOM_TINT,
    iconTint: Color = MaterialTheme.colors.iconTintPrimary,
    iconSizeType: BackgroundRoundedIconSizeType = NORMAL,
) {
    val backgroundSize = when(iconSizeType) {
        NORMAL -> 40.dp
        LARGE -> 56.dp
    }

    val iconSize = when(iconSizeType) {
        NORMAL -> 24.dp
        LARGE -> 32.dp
    }

    BackgroundRoundedIcon(
        modifier = modifier,
        iconRes = iconRes,
        backgroundTint = backgroundTint,
        tintMode = tintMode,
        iconTint = iconTint,
        backgroundSize = backgroundSize,
        iconSize = iconSize
    )
}

@Composable
fun SecondaryBackgroundRoundedIcon(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    backgroundTint: Color = MaterialTheme.colors.iconBackgroundSecondary,
    tintMode: BackgroundRoundedIconTintMode = CUSTOM_TINT,
    iconTint: Color = MaterialTheme.colors.iconTintSecondary,
    iconSizeType: BackgroundRoundedIconSizeType = NORMAL,
) {
    val backgroundSize = when(iconSizeType) {
        NORMAL -> 40.dp
        LARGE -> 56.dp
    }

    val iconSize = when(iconSizeType) {
        NORMAL -> 24.dp
        LARGE -> 32.dp
    }

    BackgroundRoundedIcon(
        modifier = modifier,
        iconRes = iconRes,
        backgroundTint = backgroundTint,
        tintMode = tintMode,
        iconTint = iconTint,
        backgroundSize = backgroundSize,
        iconSize = iconSize
    )
}

@Composable
fun TertiaryBackgroundRoundedIcon(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    backgroundTint: Color = MaterialTheme.colors.iconBackgroundTertiary,
    tintMode: BackgroundRoundedIconTintMode = CUSTOM_TINT,
    iconTint: Color = MaterialTheme.colors.iconTintTertiary,
    iconSizeType: BackgroundRoundedIconSizeType = NORMAL,
) {
    val backgroundSize = when(iconSizeType) {
        NORMAL -> 40.dp
        LARGE -> 56.dp
    }

    val iconSize = when(iconSizeType) {
        NORMAL -> 24.dp
        LARGE -> 32.dp
    }

    BackgroundRoundedIcon(
        modifier = modifier,
        iconRes = iconRes,
        backgroundTint = backgroundTint,
        tintMode = tintMode,
        iconTint = iconTint,
        backgroundSize = backgroundSize,
        iconSize = iconSize
    )
}


/**
 * wrap icon in a rounded background
 *
 * @param iconTint icon tint value will only be applied when [tintMode] set to CUSTOM_TINT
 */
@Composable
fun BackgroundRoundedIcon(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    backgroundTint: Color = MaterialTheme.colors.iconBackgroundTertiary,
    tintMode: BackgroundRoundedIconTintMode = NO_TINT,
    iconTint: Color = Color.Unspecified,
    backgroundSize: Dp = 40.dp,
    iconSize: Dp = 24.dp,
    painter: Painter = painterResource(id = iconRes)
) {
    Box(
        modifier = modifier
            .size(backgroundSize)
            .background(
                color = backgroundTint,
                shape = MaterialTheme.customShapes.fiftyPercentAllCornersRounded
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painter,
            contentDescription = "Icon",
            modifier = Modifier
                .size(iconSize)
                .fillMaxSize()
                .align(Alignment.Center),
            tint = when (tintMode) {
                NO_TINT -> Color.Unspecified
                CUSTOM_TINT -> iconTint
            }
        )
    }
}

@Composable
fun BackgroundRoundedIcon(
    modifier: Modifier = Modifier,
    backgroundTint: Color = MaterialTheme.colors.iconBackgroundTertiary,
    tintMode: BackgroundRoundedIconTintMode = NO_TINT,
    iconTint: Color = Color.Unspecified,
    backgroundSize: Dp = 40.dp,
    iconSize: Dp = 24.dp,
    bitmap: ImageBitmap
) {
    Box(
        modifier = modifier
            .size(backgroundSize)
            .background(
                color = backgroundTint,
                shape = MaterialTheme.customShapes.fiftyPercentAllCornersRounded
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            bitmap = bitmap,
            contentDescription = "Icon",
            modifier = Modifier
                .size(iconSize)
                .fillMaxSize()
                .align(Alignment.Center),
            tint = when (tintMode) {
                NO_TINT -> Color.Unspecified
                CUSTOM_TINT -> iconTint
            }
        )
    }
}
