package com.airwallex.core.designsystem.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.airwallex.core.designsystem.theme.DayNightColor
import com.airwallex.core.designsystem.theme.AirwallexColors
import com.airwallex.core.designsystem.theme.isDisplayNightMode

@Composable
fun resolveDayNightColor(dayColor: Color, nightColor: Color) =
    if (isDisplayNightMode())
        nightColor
    else
        dayColor

@Composable
fun resolveDayNightColor(dayNightColor: DayNightColor) =
    if (isDisplayNightMode())
        dayNightColor.dark
    else
        dayNightColor.light

fun Color.toHexCode(): String {
    val red = this.red * 255
    val green = this.green * 255
    val blue = this.blue * 255
    return String.format("#%02x%02x%02x", red.toInt(), green.toInt(), blue.toInt())
}

/**
 * supports only background colors for now, add more when we need in future
 */
fun findColorByName(name: String) =
    when (name.lowercase()) {
        "neutrals100" -> AirwallexColors.Neutrals100
        "neutrals200" -> AirwallexColors.Neutrals200
        "neutrals300" -> AirwallexColors.Neutrals300
        "neutrals400" -> AirwallexColors.Neutrals400
        "neutrals500" -> AirwallexColors.Neutrals500
        "neutrals600" -> AirwallexColors.Neutrals600
        "neutrals700" -> AirwallexColors.Neutrals700
        "neutrals800" -> AirwallexColors.Neutrals800
        "neutrals900" -> AirwallexColors.Neutrals900
        else -> Color.Transparent
    }
