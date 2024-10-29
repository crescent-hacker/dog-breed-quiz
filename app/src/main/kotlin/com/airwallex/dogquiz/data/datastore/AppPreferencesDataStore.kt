package com.airwallex.dogquiz.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import com.airwallex.core.datastore.preferences.PreferencesDataStore
import com.airwallex.core.datastore.preferences.stringPreferences
import com.airwallex.core.designsystem.theme.AppThemeMode
import com.airwallex.dogquiz.data.datastore.AppPreferencesDataStore.PreferenceKeys.APP_THEME_MODE
import com.airwallex.dogquiz.data.datastore.AppPreferencesDataStore.PreferenceKeys.DEVICE_ID
import com.airwallex.feature.shared.data.datastore.appThemeModePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppPreferencesDataStore(
    ctx: Context,
) : PreferencesDataStore(ctx, dataStoreName = "App") {
    private object PreferenceKeys {
        val DEVICE_ID = stringPreferencesKey("DEVICE_ID")
        val APP_THEME_MODE = stringPreferencesKey("APP_THEME_MODE")
    }

    var deviceID by stringPreferences(key = DEVICE_ID)
    val watchDeviceID = DEVICE_ID.watchValue("")

    var appThemeMode by appThemeModePreferences(key = APP_THEME_MODE)
    val watchAppThemeMode: Flow<AppThemeMode> = APP_THEME_MODE.watchValue().map { it?.let(
        AppThemeMode::valueOf) ?: AppThemeMode.DEFAULT }
}
