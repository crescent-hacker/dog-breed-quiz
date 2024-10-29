package com.airwallex.feature.shared.data.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.airwallex.core.designsystem.theme.AppThemeMode
import com.airwallex.core.datastore.preferences.CustomPreferencesPropertyDelegate
import com.airwallex.core.datastore.preferences.PreferencesDataStore
import com.airwallex.core.datastore.preferences.getValue
import com.airwallex.core.datastore.preferences.setValue
import kotlin.reflect.KProperty

/**
 * AppThemeMode preferences property delegate function
 */
fun <T : PreferencesDataStore> appThemeModePreferences(
    name: String? = null,
    key: Preferences.Key<String>? = name?.let(::stringPreferencesKey),
    defaultValue: AppThemeMode = AppThemeMode.DEFAULT
) = AppThemeModePreferencesPropertyDelegate<T>(key, defaultValue)

/**
 * AppThemeMode preferences property delegate class
 */
class AppThemeModePreferencesPropertyDelegate<T : PreferencesDataStore>(
    override val key: Preferences.Key<String>?,
    override val defaultValue: AppThemeMode
) : CustomPreferencesPropertyDelegate<T, AppThemeMode> {
    override fun getValue(thisRef: T, property: KProperty<*>): AppThemeMode =
        with(thisRef) {
            val prefKey = key ?: stringPreferencesKey(property.name)
            context.dataStore.getValue(prefKey, defaultValue = defaultValue.name).let(AppThemeMode::valueOf)
        }

    override fun setValue(thisRef: T, property: KProperty<*>, value: AppThemeMode) {
        with(thisRef) {
            val prefKey = key ?: stringPreferencesKey(property.name)
            context.dataStore.setValue(prefKey, value.name)
        }
    }
}
