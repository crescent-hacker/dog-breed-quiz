package com.airwallex.core.designsystem.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.airwallex.core.designsystem.R

/**
 * material lib typography
 */
val AirwallexTypography = Typography(
        h1 = AirwallexTextStyles.H1,
        h2 = AirwallexTextStyles.H2,
        h3 = AirwallexTextStyles.H3,
        h4 = AirwallexTextStyles.H4,
        h5 = AirwallexTextStyles.H5,
        h6 = AirwallexTextStyles.H6,
        subtitle1 = AirwallexTextStyles.Subtitle1,
        subtitle2 = AirwallexTextStyles.Subtitle2,
        body1 = AirwallexTextStyles.Body1,
        body2 = AirwallexTextStyles.Body2,
        button = AirwallexTextStyles.Subtitle3,
        caption = AirwallexTextStyles.Caption2,
        overline = AirwallexTextStyles.Caption4
)

/**
 * these typography exts is to complement the typography slots in design system
 * but material design lib cannot provide
 */
val Typography.h0 get() = AirwallexTextStyles.H0
val Typography.h1 get() = AirwallexTextStyles.H1
val Typography.h7 get() = AirwallexTextStyles.H7
val Typography.h8 get() = AirwallexTextStyles.H8
val Typography.subtitle3 get() = AirwallexTextStyles.Subtitle3
val Typography.subtitle4 get() = AirwallexTextStyles.Subtitle4
val Typography.body3 get() = AirwallexTextStyles.Body3
val Typography.body4 get() = AirwallexTextStyles.Body4
val Typography.caption1 get() = AirwallexTextStyles.Caption1
val Typography.caption2 get() = AirwallexTextStyles.Caption2
val Typography.caption3 get() = AirwallexTextStyles.Caption3
val Typography.caption4 get() = AirwallexTextStyles.Caption4

/**
 * Text styles defined by design system
 */
object AirwallexTextStyles {
    val H0: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 54.sp,
            lineHeight = 70.sp,
            letterSpacing = (-0.8).sp,
            fontFamily = FontFamily(Font(R.font.com_airwallex_core_designsystem_dm_sans_bold))
    )
    val H1: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            lineHeight = 40.sp,
            letterSpacing = (-0.5).sp,
            fontFamily = FontFamily(Font(R.font.com_airwallex_core_designsystem_dm_sans_bold))
    )
    val H2: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 32.sp,
            lineHeight = 40.sp,
            letterSpacing = (-0.5).sp,
            fontFamily = FontFamily(Font(R.font.com_airwallex_core_designsystem_dm_sans_regular))
    )
    val H3: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            lineHeight = 34.sp,
            letterSpacing = (-0.38).sp,
            fontFamily = FontFamily(Font(R.font.com_airwallex_core_designsystem_dm_sans_bold))
    )
    val H4: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 28.sp,
            lineHeight = 34.sp,
            letterSpacing = (-0.38).sp,
            fontFamily = FontFamily(Font(R.font.com_airwallex_core_designsystem_dm_sans_regular))
    )
    val H5: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = (-0.26).sp,
            fontFamily = FontFamily(Font(R.font.com_airwallex_core_designsystem_dm_sans_bold))
    )
    val H6: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = (-0.26).sp,
            fontFamily = FontFamily(Font(R.font.com_airwallex_core_designsystem_dm_sans_regular))
    )
    val H7: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 24.sp,
            letterSpacing = (-0.45).sp,
            fontFamily = FontFamily(Font(R.font.com_airwallex_core_designsystem_dm_sans_bold))
    )
    val H8: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            lineHeight = 24.sp,
            letterSpacing = (-0.45).sp,
            fontFamily = FontFamily(Font(R.font.com_airwallex_core_designsystem_dm_sans_regular))
    )
    val Subtitle1: TextStyle = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 17.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.sp,
            fontFamily = FontFamily(Font(R.font.com_airwallex_core_designsystem_roboto_bold))
    )
    val Subtitle2: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 17.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.sp,
            fontFamily = FontFamily(Font(R.font.com_airwallex_core_designsystem_roboto_regular))
    )
    val Subtitle3: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp,
            fontFamily = FontFamily(Font(R.font.com_airwallex_core_designsystem_roboto_medium))
    )
    val Subtitle4: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp,
            fontFamily = FontFamily(Font(R.font.com_airwallex_core_designsystem_roboto_regular))
    )
    val Body1: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            lineHeight = 22.sp,
            letterSpacing = 0.15.sp,
            fontFamily = FontFamily(Font(R.font.com_airwallex_core_designsystem_roboto_medium))
    )
    val Body2: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            lineHeight = 22.sp,
            letterSpacing = 0.15.sp,
            fontFamily = FontFamily(Font(R.font.com_airwallex_core_designsystem_roboto_regular))
    )
    val Body3: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.1.sp,
            fontFamily = FontFamily(Font(R.font.com_airwallex_core_designsystem_roboto_medium))
    )
    val Body4: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.1.sp,
            fontFamily = FontFamily(Font(R.font.com_airwallex_core_designsystem_roboto_regular))
    )
    val Caption1: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.4.sp,
            fontFamily = FontFamily(Font(R.font.com_airwallex_core_designsystem_roboto_medium))
    )
    val Caption2: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.4.sp,
            fontFamily = FontFamily(Font(R.font.com_airwallex_core_designsystem_roboto_regular))
    )
    val Caption3: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.4.sp,
            fontFamily = FontFamily(Font(R.font.com_airwallex_core_designsystem_roboto_medium))
    )
    val Caption4: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.4.sp,
            fontFamily = FontFamily(Font(R.font.com_airwallex_core_designsystem_roboto_regular))
    )
}


