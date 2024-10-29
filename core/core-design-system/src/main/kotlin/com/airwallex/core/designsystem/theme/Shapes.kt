package com.airwallex.core.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.unit.dp

object AirwallexCustomShapes {
    val extraSmallAllCornersRounded get() = RoundedCornerShape(8.dp)
    val smallAllCornersRounded get() = RoundedCornerShape(16.dp)
    val mediumAllCornersRounded get() = RoundedCornerShape(24.dp)
    val largeAllCornersRounded get() = RoundedCornerShape(32.dp)

    // 50% rounded
    val fiftyPercentAllCornersRounded get() = RoundedCornerShape(50)
    // top corners rounded with medium size
    val mediumTopCornersRounded = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    val smallRightCornersRounded = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)

    val fiftyPercentLeftCornersRounded get() = RoundedCornerShape(topStartPercent = 50, bottomStartPercent = 50)
    val fiftyPercentRightCornersRounded get() = RoundedCornerShape(topEndPercent = 50, bottomEndPercent = 50)
}

val MaterialTheme.customShapes get() = AirwallexCustomShapes

