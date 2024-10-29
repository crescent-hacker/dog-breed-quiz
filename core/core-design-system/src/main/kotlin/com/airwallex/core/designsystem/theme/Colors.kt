package com.airwallex.core.designsystem.theme

import android.annotation.SuppressLint
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import com.airwallex.core.designsystem.theme.AirwallexColors.Neutrals10
import com.airwallex.core.designsystem.theme.AirwallexColors.Neutrals50
import com.airwallex.core.designsystem.theme.AirwallexColors.Neutrals700

/**
 * Design system light colors in Material lib
 */
@SuppressLint("ConflictingOnColor")
val AirwallexLightColorPalette = lightColors(
    primary = AirwallexColors.LightYear,
    primaryVariant = AirwallexColors.LightYear700,
    secondary = AirwallexColors.SecondaryPink,
    secondaryVariant = AirwallexColors.SecondaryPink300,
    background = AirwallexColors.White,
    onBackground = AirwallexColors.Neutrals900,
    surface = AirwallexColors.Neutrals50,
    error = AirwallexColors.ErrorRed500,
    onPrimary = AirwallexColors.Neutrals10,
    onSecondary = AirwallexColors.Neutrals10,
    onSurface = AirwallexColors.Neutrals900,
    onError = AirwallexColors.White,
)

/**
 * Design system night colors in Material lib
 */
@SuppressLint("ConflictingOnColor")
val AirwallexDarkColorPalette = darkColors(
    primary = AirwallexColors.LightYear300,
    primaryVariant = AirwallexColors.LightYear50,
    secondary = AirwallexColors.SecondaryPink,
    secondaryVariant = AirwallexColors.SecondaryPink300,
    background = AirwallexColors.Neutrals900,
    onBackground = AirwallexColors.Neutrals10,
    surface = AirwallexColors.Neutrals800,
    error = AirwallexColors.ErrorRed300,
    onPrimary = AirwallexColors.Neutrals900,
    onSecondary = AirwallexColors.Neutrals900,
    onSurface = AirwallexColors.Neutrals10,
    onError = AirwallexColors.ErrorRed800
)

/**
 * these color exts is to complement the color slots in design system
 * but material design lib cannot provide
 */
val Colors.surfaceVariant: Color
    get() = if (isLight) AirwallexColors.Neutrals100 else AirwallexColors.Neutrals700

val Colors.surfaceVariantModal: Color
    get() = if (isLight) AirwallexColors.Neutrals50 else AirwallexColors.Neutrals700

val Colors.surfaceVariantModalTransparent: Color
    get() = if (isLight) AirwallexColors.Neutrals50 else AirwallexColors.Neutrals800

val Colors.onSurfaceVariant: Color
    get() = if (isLight) AirwallexColors.Neutrals300 else AirwallexColors.Neutrals300

val Colors.textPrimary: Color
    get() = if (isLight) AirwallexColors.Neutrals900 else AirwallexColors.Neutrals10

val Colors.textSecondary: Color
    get() = if (isLight) AirwallexColors.Neutrals700 else AirwallexColors.Neutrals200

val Colors.textTertiary: Color
    get() = if (isLight) AirwallexColors.Neutrals500 else AirwallexColors.Neutrals300

val Colors.textQuaternary: Color
    get() = if (isLight) AirwallexColors.Neutrals300 else AirwallexColors.Neutrals400

val Colors.textLink: Color
    get() = if (isLight) AirwallexColors.LightYear else AirwallexColors.LightYear300

val Colors.textError: Color
    get() = if (isLight) AirwallexColors.ErrorRed500 else AirwallexColors.ErrorRed300

val Colors.textHint: Color
    get() = if (isLight) AirwallexColors.Neutrals200 else AirwallexColors.Neutrals400

val Colors.buttonBackgroundPrimary: Color
    get() = if (isLight) AirwallexColors.LightYear else AirwallexColors.LightYear300
val Colors.buttonTextPrimary: Color
    get() = if (isLight) AirwallexColors.Neutrals10 else AirwallexColors.Neutrals900

val Colors.buttonBackgroundSecondary: Color
    get() = if (isLight) AirwallexColors.Neutrals10 else AirwallexColors.Neutrals900
val Colors.buttonTextSecondary: Color
    get() = if (isLight) AirwallexColors.LightYear else AirwallexColors.Neutrals10
val Colors.buttonBorderSecondary: Color
    get() = if (isLight) AirwallexColors.Neutrals600 else AirwallexColors.Neutrals100

val Colors.buttonBackgroundTertiary: Color
    get() = if (isLight) AirwallexColors.LightYear100 else AirwallexColors.Neutrals700
val Colors.buttonBackgroundModal: Color
    get() = if (isLight) AirwallexColors.LightYear100 else AirwallexColors.LightYear400.copy(alpha = 0.2f)
val Colors.buttonBackgroundDisabled: Color
    get() = if (isLight) AirwallexColors.Neutrals100 else AirwallexColors.Neutrals700
val Colors.buttonTextTertiary: Color
    get() = if (isLight) AirwallexColors.LightYear else AirwallexColors.Neutrals10

val Colors.buttonBackgroundQuaternary: Color
    get() = if (isLight) AirwallexColors.Neutrals50 else AirwallexColors.Neutrals800
val Colors.buttonTextQuaternary: Color
    get() = if (isLight) AirwallexColors.Neutrals900 else AirwallexColors.Neutrals10

val Colors.textButtonBackground: Color
    get() = if (isLight) AirwallexColors.Transparent else AirwallexColors.Transparent
val Colors.textButtonText: Color
    get() = textLink

val Colors.iconTintPrimary: Color
    get() = if (isLight) AirwallexColors.Neutrals900 else AirwallexColors.LightYear300
val Colors.iconTintSelectionPrimary: Color
    get() = if (isLight) AirwallexColors.LightYear600 else AirwallexColors.LightYear300
val Colors.iconTintSecondary: Color
    get() = if (isLight) AirwallexColors.LightYear else AirwallexColors.LightYear300
val Colors.iconTintDisabled: Color
    get() = if (isLight) AirwallexColors.Neutrals900.copy(alpha = .3f) else Neutrals10.copy(alpha = .3f)
val Colors.iconTintTertiary: Color
    get() = textPrimary
val Colors.iconTintQuaternary: Color
    get() = if (isLight) AirwallexColors.LightYear600 else AirwallexColors.LightYear300
val Colors.transparentBackground: Color
    get() = if (isLight) AirwallexColors.Transparent else AirwallexColors.Transparent
val Colors.iconBackgroundPrimary: Color
    get() = if (isLight) AirwallexColors.Neutrals100 else AirwallexColors.LightYear400.copy(alpha = 0.2f)
val Colors.iconBackgroundSecondary: Color
    get() = if (isLight) AirwallexColors.LightYear100 else AirwallexColors.LightYear400.copy(alpha = 0.2f)
val Colors.iconBackgroundTertiary: Color
    get() = if (isLight) AirwallexColors.Neutrals100 else AirwallexColors.Neutrals700
val Colors.iconBackgroundQuaternary: Color
    get() = if (isLight) AirwallexColors.Neutrals50 else AirwallexColors.Neutrals600

val Colors.iconBoundaryPrimary: Color
    get() = if (isLight) AirwallexColors.LightYear600 else AirwallexColors.LightYear300

val Colors.snackBarBackground: Color
    get() = if (isLight) AirwallexColors.Neutrals900 else AirwallexColors.Neutrals50
val Colors.snackBarContent: Color
    get() = if (isLight) AirwallexColors.Neutrals10 else AirwallexColors.Neutrals900

val Colors.checkedBox: Color
    get() = if (isLight) AirwallexColors.LightYear600 else AirwallexColors.LightYear300

val uncheckedBox: Color
    get() = AirwallexColors.Transparent

val Colors.checkmark: Color
    get() = if (isLight) AirwallexColors.Neutrals10 else AirwallexColors.Neutrals900

val Colors.uncheckedBorderColor: Color
    get() = if (isLight) AirwallexColors.Neutrals300 else AirwallexColors.Neutrals400

val Colors.checkedBorderColor: Color
    get() = if (isLight) AirwallexColors.LightYear600 else AirwallexColors.LightYear300


/**
 * declare colors from design system
 */
object AirwallexColors {
    val White = Color(0xFFFFFFFF)
    val Black = Color(0xFF000000)
    val Grey = Color(0xFFFAFAFA)
    val Transparent = Color(0x00FFFFFF)

    // purple blue color series
    val PrimaryPurple10 = Color(0xFFFFFBFF)
    val PrimaryPurple50 = Color(0xFFF2EFFF)
    val PrimaryPurple100 = Color(0xFFE2DFFF)
    val PrimaryPurple200 = Color(0xFFC2C1FF)
    val PrimaryPurple300 = Color(0xFFA3A2FF)
    val PrimaryPurple400 = Color(0xFF8482FF)
    val PrimaryPurple600 = Color(0xFF4941EC)
    val PrimaryPurple700 = Color(0xFF2E1CD5)
    val PrimaryPurple800 = Color(0xFF1A00A6)
    val PrimaryPurple900 = Color(0xFF0C006A)
    val PrimaryPurple = PrimaryPurple600

    // pink color series
    val SecondaryPink50 = Color(0xFFFFEBF8)
    val SecondaryPink100 = Color(0xFFFFD6F7)
    val SecondaryPink200 = Color(0xFFFFAAF8)
    val SecondaryPink300 = Color(0xFFFF74FD)
    val SecondaryPink400 = Color(0xFFE54FE7)
    val SecondaryPink500 = Color(0xFFC62FCB)
    val SecondaryPink600 = Color(0xFFA700AD)
    val SecondaryPink700 = Color(0xFF800084)
    val SecondaryPink800 = Color(0xFF5A005E)
    val SecondaryPink900 = Color(0xFF37003A)
    val SecondaryPink = SecondaryPink400

    // Neutrals color series
    val Neutrals10 = Color(0xFFFDFDFD)
    val Neutrals50 = Color(0xFFF6F6F7)
    val Neutrals100 = Color(0xFFEDEDEE)
    val Neutrals200 = Color(0xFFC6C6C8)
    val Neutrals300 = Color(0xFFACACAE)
    val Neutrals400 = Color(0xFF77777B)
    val Neutrals500 = Color(0xFF5F5F63)
    val Neutrals600 = Color(0xFF47474C)
    val Neutrals700 = Color(0xFF36363B)
    val Neutrals800_a80 = Color(0xFF212127)
    val Neutrals800 = Color(0xFF24242A)
    val Neutrals900 = Color(0xFF141417)
    val Neutrals = Neutrals400

    // red color series
    val ErrorRed50 = Color(0xFFFFEDEA)
    val ErrorRed100 = Color(0xFFFFDAD5)
    val ErrorRed200 = Color(0xFFFFB4AA)
    val ErrorRed300 = Color(0xFFFF8A7C)
    val ErrorRed400 = Color(0xFFFF5447)
    val ErrorRed500 = Color(0xFFE23029)
    val ErrorRed600 = Color(0xFFBD0E12)
    val ErrorRed700 = Color(0xFF930007)
    val ErrorRed800 = Color(0xFF690004)
    val ErrorRed900 = Color(0xFF410001)
    val ErrorRed = ErrorRed400

    // green color series
    val SuccessGreen10 = Color(0xFFF6FFF1)
    val SuccessGreen50 = Color(0xFFC7FFC4)
    val SuccessGreen100 = Color(0xFF6CFF82)
    val SuccessGreen200 = Color(0xFF47E266)
    val SuccessGreen300 = Color(0xFF1BC54E)
    val SuccessGreen400 = Color(0xFF00A73E)
    val SuccessGreen500 = Color(0xFF008A32)
    val SuccessGreen600 = Color(0xFF006E26)
    val SuccessGreen700 = Color(0xFF00531A)
    val SuccessGreen800 = Color(0xFF003910)
    val SuccessGreen900 = Color(0xFF002106)
    val SuccessGreen = SuccessGreen300

    // blue color series
    val BlueMoon10 = Color(0xFFFDFCFF)
    val BlueMoon50 = Color(0xFFEAF1FF)
    val BlueMoon100 = Color(0xFFD3E4FF)
    val BlueMoon200 = Color(0xFFA2C9FF)
    val BlueMoon300 = Color(0xFF6BAEFF)
    val BlueMoon400 = Color(0xFF1D93FA)
    val BlueMoon500 = Color(0xFF0079D3)
    val BlueMoon600 = Color(0xFF0060A9)
    val BlueMoon700 = Color(0xFF004881)
    val BlueMoon800 = Color(0xFF00315B)
    val BlueMoon900 = Color(0xFF001C38)
    val BlueMoon = BlueMoon400

    // purple color series
    val Twilight50 = Color(0xFFF7EDFF)
    val Twilight100 = Color(0xFFECDCFF)
    val Twilight200 = Color(0xFFD5BBFF)
    val Twilight300 = Color(0xFFBE98FF)
    val Twilight400 = Color(0xFFA874FF)
    val Twilight500 = Color(0xFF9150F6)
    val Twilight600 = Color(0xFF7731DC)
    val Twilight700 = Color(0xFF5E00C1)
    val Twilight800 = Color(0xFF41008B)
    val Twilight900 = Color(0xFF270058)
    val Twilight = Twilight600


    // yellow color series
    val LightYear50 = Color(0xFFFFEED7)
    val LightYear100 = Color(0xFFFFDEA7)
    val LightYear200 = Color(0xFFFFBB18)
    val LightYear300 = Color(0xFFDEA000)
    val LightYear400 = Color(0xFFBC8700)
    val LightYear500 = Color(0xFF9B6F00)
    val LightYear600 = Color(0xFF7C5800)
    val LightYear700 = Color(0xFF5E4200)
    val LightYear800 = Color(0xFF412D00)
    val LightYear900 = Color(0xFF271900)
    val LightYear = LightYear200
}

/****************************************************************************
 *                      extended colors                                     *
 ****************************************************************************/

data class ExtendedColors(
    val placeholder: Color,
    val fullScreenLoaderBackground: Color,
    val success: Color,
)

val AirwallexDarkExtendedColorPalette = ExtendedColors(
    placeholder = Neutrals700,
    fullScreenLoaderBackground = AirwallexColors.LightYear900,
    success = AirwallexColors.SuccessGreen300
)

val AirwallexLightExtendedColorPalette = ExtendedColors(
    placeholder = Neutrals50,
    fullScreenLoaderBackground = AirwallexColors.LightYear100,
    success = AirwallexColors.SuccessGreen300
)

val MaterialTheme.extendedColors
    @Composable
    @ReadOnlyComposable
    get() = if (colors.isLight) AirwallexLightExtendedColorPalette else AirwallexDarkExtendedColorPalette

