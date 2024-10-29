package com.airwallex.core.designsystem.theme

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Composable
fun BaseAppTheme(content: @Composable () -> Unit) {
    val displayNightMode = LocalNightMode.current

    MaterialTheme(
        typography = AirwallexTypography,
        colors = if (displayNightMode) AirwallexDarkColorPalette else AirwallexLightColorPalette,
    ) {
        CompositionLocalProvider(
            LocalNightMode provides displayNightMode
        ) {
            content()
        }
    }
}

@Composable
fun DarkAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = AirwallexTypography,
        colors = AirwallexDarkColorPalette
    ) {
        CompositionLocalProvider(
            LocalNightMode provides true
        ) {
            content()
        }
    }
}

@Composable
fun LightAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = AirwallexTypography,
        colors = AirwallexLightColorPalette
    ) {
        CompositionLocalProvider(
            LocalNightMode provides false
        ) {
            content()
        }
    }
}

fun getCurrentAppCompatThemeMode() = when (AppCompatDelegate.getDefaultNightMode()) {
    AppCompatDelegate.MODE_NIGHT_NO -> AppThemeMode.MODE_DAY
    AppCompatDelegate.MODE_NIGHT_YES -> AppThemeMode.MODE_NIGHT
    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> AppThemeMode.MODE_AUTO
    else -> AppThemeMode.MODE_AUTO // default mode
}

fun applyAppDefaultNightMode(themeMode: AppThemeMode) {
    val delegateNightMode = when (themeMode) {
        AppThemeMode.MODE_DAY -> AppCompatDelegate.MODE_NIGHT_NO
        AppThemeMode.MODE_NIGHT -> AppCompatDelegate.MODE_NIGHT_YES
        AppThemeMode.MODE_AUTO -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }
    AppCompatDelegate.setDefaultNightMode(delegateNightMode)
}

@Composable
fun isDisplayNightMode(appThemeMode: AppThemeMode = LocalAppThemeMode.current) = when (appThemeMode) {
    AppThemeMode.MODE_DAY -> false
    AppThemeMode.MODE_NIGHT -> true
    AppThemeMode.MODE_AUTO -> isSystemInDarkTheme()
}

enum class AppThemeMode(val key: String) {
    MODE_DAY("MODE_DAY"),
    MODE_NIGHT("MODE_NIGHT"),
    MODE_AUTO("MODE_AUTO");

    companion object {
        val DEFAULT = MODE_AUTO
    }
}


val LocalNightMode = staticCompositionLocalOf<Boolean> {
    error("NightMode flag is not provided.")
}

val LocalAppThemeMode = staticCompositionLocalOf<AppThemeMode> {
    error("AppThemeMode is not provided.")
}

data class DayNightColor(
    val dark: Color = AirwallexDarkColorPalette.surface,
    val light: Color = AirwallexLightColorPalette.surface
)
