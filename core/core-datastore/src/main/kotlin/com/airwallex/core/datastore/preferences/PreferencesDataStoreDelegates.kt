package com.airwallex.core.datastore.preferences

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import com.airwallex.core.util.json.kotlinJson
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface PreferencesPropertyDelegate<P : PreferencesDataStore, T> : ReadWriteProperty<P, T> {
    val key: Preferences.Key<T>?
    val defaultValue: T
}

interface CustomPreferencesPropertyDelegate<P : PreferencesDataStore, T> : ReadWriteProperty<P, T> {
    val key: Preferences.Key<String>?
    val defaultValue: T
}

/**
 * Boolean preferences property delegate function
 */
fun <T : PreferencesDataStore> booleanPreferences(
    name: String? = null,
    key: Preferences.Key<Boolean>? = name?.let(::booleanPreferencesKey),
    defaultValue: Boolean = false
) = BooleanPreferencesPropertyDelegate<T>(key, defaultValue)

/**
 * Boolean preferences property delegate class
 */
class BooleanPreferencesPropertyDelegate<T : PreferencesDataStore>(
    override val key: Preferences.Key<Boolean>?,
    override val defaultValue: Boolean
) : PreferencesPropertyDelegate<T, Boolean> {
    override fun getValue(thisRef: T, property: KProperty<*>): Boolean =
        with(thisRef) {
            val prefKey = key ?: booleanPreferencesKey(property.name)
            context.dataStore.getValue(prefKey, defaultValue = defaultValue)
        }

    override fun setValue(thisRef: T, property: KProperty<*>, value: Boolean) {
        with(thisRef) {
            val prefKey = key ?: booleanPreferencesKey(property.name)
            context.dataStore.setValue(prefKey, value)
        }
    }
}

/**
 * Long preferences property delegate function
 */
fun <T : PreferencesDataStore> longPreferences(
    name: String? = null,
    key: Preferences.Key<Long>? = name?.let(::longPreferencesKey),
    defaultValue: Long = 0L
) = LongPreferencesPropertyDelegate<T>(key, defaultValue)

/**
 * Long preferences property delegate class
 */
class LongPreferencesPropertyDelegate<T : PreferencesDataStore>(
    override val key: Preferences.Key<Long>?,
    override val defaultValue: Long
) : PreferencesPropertyDelegate<T, Long> {
    override fun getValue(thisRef: T, property: KProperty<*>): Long =
        with(thisRef) {
            val prefKey = key ?: longPreferencesKey(property.name)
            context.dataStore.getValue(prefKey, defaultValue = defaultValue)
        }

    override fun setValue(thisRef: T, property: KProperty<*>, value: Long) {
        with(thisRef) {
            val prefKey = key ?: longPreferencesKey(property.name)
            context.dataStore.setValue(prefKey, value)
        }
    }
}

/**
 * Float preferences property delegate function
 */
fun <T : PreferencesDataStore> floatPreferences(
    name: String? = null,
    key: Preferences.Key<Float>? = name?.let(::floatPreferencesKey),
    defaultValue: Float = 0f
) = FloatPreferencesPropertyDelegate<T>(key, defaultValue)

/**
 * Float preferences property delegate class
 */
class FloatPreferencesPropertyDelegate<T : PreferencesDataStore>(
    override val key: Preferences.Key<Float>?,
    override val defaultValue: Float
) : PreferencesPropertyDelegate<T, Float> {
    override fun getValue(thisRef: T, property: KProperty<*>): Float =
        with(thisRef) {
            val prefKey = key ?: floatPreferencesKey(property.name)
            context.dataStore.getValue(prefKey, defaultValue = defaultValue)
        }

    override fun setValue(thisRef: T, property: KProperty<*>, value: Float) {
        with(thisRef) {
            val prefKey = key ?: floatPreferencesKey(property.name)
            context.dataStore.setValue(prefKey, value)
        }
    }
}


/**
 * Double preferences property delegate function
 */
fun <T : PreferencesDataStore> doublePreferences(
    name: String? = null,
    key: Preferences.Key<Double>? = name?.let(::doublePreferencesKey),
    defaultValue: Double = 0.0
) = DoublePreferencesPropertyDelegate<T>(key, defaultValue)

/**
 * Double preferences property delegate class
 */
class DoublePreferencesPropertyDelegate<T : PreferencesDataStore>(
    override val key: Preferences.Key<Double>?,
    override val defaultValue: Double
) : PreferencesPropertyDelegate<T, Double> {
    override fun getValue(thisRef: T, property: KProperty<*>): Double =
        with(thisRef) {
            val prefKey = key ?: doublePreferencesKey(property.name)
            context.dataStore.getValue(prefKey, defaultValue = defaultValue)
        }

    override fun setValue(thisRef: T, property: KProperty<*>, value: Double) {
        with(thisRef) {
            val prefKey = key ?: doublePreferencesKey(property.name)
            context.dataStore.setValue(prefKey, value)
        }
    }
}


/**
 * Integer preferences property delegate function
 */
fun <T : PreferencesDataStore> intPreferences(
    name: String? = null,
    key: Preferences.Key<Int>? = name?.let(::intPreferencesKey),
    defaultValue: Int = 0
) = IntPreferencesPropertyDelegate<T>(key, defaultValue)

/**
 * Integer preferences property delegate class
 */
class IntPreferencesPropertyDelegate<T : PreferencesDataStore>(
    override val key: Preferences.Key<Int>?,
    override val defaultValue: Int
) : PreferencesPropertyDelegate<T, Int> {
    override fun getValue(thisRef: T, property: KProperty<*>): Int =
        with(thisRef) {
            val prefKey = key ?: intPreferencesKey(property.name)
            context.dataStore.getValue(prefKey, defaultValue = defaultValue)
        }

    override fun setValue(thisRef: T, property: KProperty<*>, value: Int) {
        with(thisRef) {
            val prefKey = key ?: intPreferencesKey(property.name)
            context.dataStore.setValue(prefKey, value)
        }
    }

}


/**
 * String preferences property delegate function
 */
fun <T : PreferencesDataStore> stringPreferences(
    name: String? = null,
    key: Preferences.Key<String>? = name?.let(::stringPreferencesKey),
    defaultValue: String = ""
) = StringPreferencesPropertyDelegate<T>(key, defaultValue)

/**
 * String preferences property delegate class
 */
class StringPreferencesPropertyDelegate<T : PreferencesDataStore>(
    override val key: Preferences.Key<String>?,
    override val defaultValue: String
) : PreferencesPropertyDelegate<T, String> {
    override fun getValue(thisRef: T, property: KProperty<*>): String =
        with(thisRef) {
            val prefKey = key ?: stringPreferencesKey(property.name)
            context.dataStore.getValue(prefKey, defaultValue = defaultValue)
        }

    override fun setValue(thisRef: T, property: KProperty<*>, value: String) {
        with(thisRef) {
            val prefKey = key ?: stringPreferencesKey(property.name)
            context.dataStore.setValue(prefKey, value)
        }
    }
}


/**
 * Secure String preferences property delegate function
 */
fun <T : PreferencesDataStore> secureStringPreferences(
    name: String? = null,
    key: Preferences.Key<String>? = name?.let(::stringPreferencesKey),
    defaultValue: String = ""
) = SecureStringPreferencesPropertyDelegate<T>(key, defaultValue)

/**
 * Secure string preferences property delegate class
 */
class SecureStringPreferencesPropertyDelegate<T : PreferencesDataStore>(
    override val key: Preferences.Key<String>?,
    override val defaultValue: String
) : PreferencesPropertyDelegate<T, String> {
    override fun getValue(thisRef: T, property: KProperty<*>): String =
        with(thisRef) {
            val prefKey = key ?: stringPreferencesKey(property.name)
            context.dataStore.getSecureSerializedValue(prefKey, defaultValue = defaultValue)
        }

    override fun setValue(thisRef: T, property: KProperty<*>, value: String) {
        with(thisRef) {
            val prefKey = key ?: stringPreferencesKey(property.name)
            context.dataStore.setSecureSerializedValue(prefKey, value)
        }
    }
}

/**
 * Flag Map preferences property delegate function
 */
fun <T : PreferencesDataStore> flagMapPreferences(
    name: String? = null,
    key: Preferences.Key<String>? = name?.let(::stringPreferencesKey),
    defaultValue: Map<String, Boolean> = emptyMap()
) = FlagMapPreferencesPropertyDelegate<T>(key, defaultValue)

/**
 * Flag Map preferences property delegate class
 */
class FlagMapPreferencesPropertyDelegate<T : PreferencesDataStore>(
    override val key: Preferences.Key<String>?,
    override val defaultValue: Map<String, Boolean>
) : CustomPreferencesPropertyDelegate<T, Map<String, Boolean>> {
    override fun getValue(thisRef: T, property: KProperty<*>): Map<String, Boolean> =
        with(thisRef) {
            val prefKey = key ?: stringPreferencesKey(property.name)
            val value = context.dataStore.getValue(prefKey, defaultValue = "")

            value.takeIf { it.isNotBlank() }
                ?.let(kotlinJson::decodeFromString)
                ?: defaultValue
        }

    override fun setValue(thisRef: T, property: KProperty<*>, value: Map<String, Boolean>) {
        with(thisRef) {
            val prefKey = key ?: stringPreferencesKey(property.name)
            context.dataStore.setValue(prefKey, kotlinJson.encodeToString(value))
        }
    }
}

/**
 * String Map preferences property delegate function
 */
fun <T : PreferencesDataStore> stringMapPreferences(
    name: String? = null,
    key: Preferences.Key<String>? = name?.let(::stringPreferencesKey),
    defaultValue: Map<String, String> = emptyMap()
) = StringMapPreferencesPropertyDelegate<T>(key, defaultValue)

/**
 * String Map preferences property delegate class
 */
class StringMapPreferencesPropertyDelegate<T : PreferencesDataStore>(
    override val key: Preferences.Key<String>?,
    override val defaultValue: Map<String, String>
) : CustomPreferencesPropertyDelegate<T, Map<String, String>> {
    override fun getValue(thisRef: T, property: KProperty<*>): Map<String, String> =
        with(thisRef) {
            val prefKey = key ?: stringPreferencesKey(property.name)
            val value = context.dataStore.getValue(prefKey, defaultValue = "")

            value.takeIf { it.isNotBlank() }
                ?.let(kotlinJson::decodeFromString)
                ?: defaultValue
        }

    override fun setValue(thisRef: T, property: KProperty<*>, value: Map<String, String>) {
        with(thisRef) {
            val prefKey = key ?: stringPreferencesKey(property.name)
            context.dataStore.setValue(prefKey, kotlinJson.encodeToString(value))
        }
    }
}


/**
 * List preferences property delegate function
 */
fun <T : PreferencesDataStore> stringListPreferences(
    name: String? = null,
    key: Preferences.Key<String>? = name?.let(::stringPreferencesKey),
    defaultValue: List<String> = emptyList()
) = StringListPreferencesPropertyDelegate<T>(key, defaultValue)

/**
 * Flag Map preferences property delegate class
 */
class StringListPreferencesPropertyDelegate<T : PreferencesDataStore>(
    override val key: Preferences.Key<String>?,
    override val defaultValue: List<String>
) : CustomPreferencesPropertyDelegate<T, List<String>> {
    override fun getValue(thisRef: T, property: KProperty<*>): List<String> =
        with(thisRef) {
            val prefKey = key ?: stringPreferencesKey(property.name)
            val value = context.dataStore.getValue(prefKey, defaultValue = "")

            value.takeIf { it.isNotBlank() }
                ?.let(kotlinJson::decodeFromString)
                ?: defaultValue
        }

    override fun setValue(thisRef: T, property: KProperty<*>, value: List<String>) {
        with(thisRef) {
            val prefKey = key ?: stringPreferencesKey(property.name)
            context.dataStore.setValue(prefKey, kotlinJson.encodeToString(value))
        }
    }
}


/**
 * Long Map preferences property delegate function
 */
fun <T : PreferencesDataStore> longMapPreferences(
    name: String? = null,
    key: Preferences.Key<String>? = name?.let(::stringPreferencesKey),
    defaultValue: Map<String, Long> = emptyMap()
) = LongMapPreferencesPropertyDelegate<T>(key, defaultValue)

/**
 * Long Map preferences property delegate class
 */
class LongMapPreferencesPropertyDelegate<T : PreferencesDataStore>(
    override val key: Preferences.Key<String>?,
    override val defaultValue: Map<String, Long>
) : CustomPreferencesPropertyDelegate<T, Map<String, Long>> {
    override fun getValue(thisRef: T, property: KProperty<*>): Map<String, Long> =
        with(thisRef) {
            val prefKey = key ?: stringPreferencesKey(property.name)
            val value = context.dataStore.getValue(prefKey, defaultValue = "")

            value.takeIf { it.isNotBlank() }
                ?.let(kotlinJson::decodeFromString)
                ?: defaultValue
        }

    override fun setValue(thisRef: T, property: KProperty<*>, value: Map<String, Long>) {
        with(thisRef) {
            val prefKey = key ?: stringPreferencesKey(property.name)
            context.dataStore.setValue(prefKey, kotlinJson.encodeToString(value))
        }
    }
}
