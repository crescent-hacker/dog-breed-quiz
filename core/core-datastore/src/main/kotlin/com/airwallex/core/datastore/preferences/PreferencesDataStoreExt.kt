@file:Suppress("UNCHECKED_CAST")

package com.airwallex.core.datastore.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import com.airwallex.core.datastore.util.SecurityUtils
import java.io.Serializable

/*******************************************************************
 *                         datastore ext                           *
 *******************************************************************/

fun <T> DataStore<Preferences>.getValue(
        key: Preferences.Key<T>,
        defaultValue: T
): T = runBlocking {
    data.first()[key] ?: defaultValue
}

fun <T> DataStore<Preferences>.setValue(
        key: Preferences.Key<T>,
        value: T?
) = runBlocking<Unit> {
    edit {
        if (value == null) {
            it.remove(key)
        } else {
            it[key] = value
        }
    }
}

/*******************************************************************
 *                   serialized datastore ext                      *
 *******************************************************************/
inline fun <reified T> DataStore<Preferences>.getSerializedValue(
    key: Preferences.Key<String>,
    defaultValue: T
): T = runBlocking {
    val value = data.first()[key]

    if (value.isNullOrBlank())
        return@runBlocking defaultValue

    when (defaultValue) {
        is Boolean -> Json.decodeFromString(deserializer = Boolean.serializer(), string = value)
        is String -> Json.decodeFromString(deserializer = String.serializer(), string = value)
        is Int -> Json.decodeFromString(deserializer = Int.serializer(), string = value)
        is Long -> Json.decodeFromString(deserializer = Long.serializer(), string = value)
        is Float -> Json.decodeFromString(deserializer = Float.serializer(), string = value)
        else -> throw IllegalStateException("Preferences value T can only be Boolean, Int, Long, Float, String, Set<String>.")
    } as T
}

fun <T> Flow<Preferences>.watchSerializedValue(
    key: Preferences.Key<String>,
    defaultValue: T
): Flow<T> = this.map { prefs ->
    val value = prefs[key]

    if (value.isNullOrBlank())
        return@map defaultValue

    when (defaultValue) {
        is Boolean -> Json.decodeFromString(deserializer = Boolean.serializer(), string = value)
        is String -> Json.decodeFromString(deserializer = String.serializer(), string = value)
        is Int -> Json.decodeFromString(deserializer = Int.serializer(), string = value)
        is Long -> Json.decodeFromString(deserializer = Long.serializer(), string = value)
        is Float -> Json.decodeFromString(deserializer = Float.serializer(), string = value)
        else -> throw IllegalStateException("Preferences value T can only be Boolean, Int, Long, Float, String, Set<String>.")
    } as T
}

inline fun <reified T : Serializable> DataStore<Preferences>.setSerializedValue(
    key: Preferences.Key<String>,
    value: T?
) = runBlocking<Unit> {
    edit {
        if (value == null) {
            it.remove(key)
        } else {
            val serializedValue = when (value) {
                is Boolean -> Json.encodeToString(serializer = Boolean.serializer(), value = value)
                is String -> Json.encodeToString(serializer = String.serializer(), value = value)
                is Int -> Json.encodeToString(serializer = Int.serializer(), value = value)
                is Long -> Json.encodeToString(serializer = Long.serializer(), value = value)
                is Float -> Json.encodeToString(serializer = Float.serializer(), value = value)
                else -> throw IllegalStateException("Preferences value T can only be Boolean, Int, Long, Float, String, Set<String>.")
            }

            it[key] = serializedValue
        }
    }
}



/*******************************************************************
 *                   secure datastore ext                           *
 *******************************************************************/
const val SECURITY_KEY_ALIAS = "AirwallexSecurityAESKeyAlias"

inline fun <reified T> DataStore<Preferences>.getSecureSerializedValue(
        key: Preferences.Key<String>,
        defaultValue: T
): T = runBlocking {
    val encryptedValue = data.first()[key]

    if (encryptedValue.isNullOrBlank())
        return@runBlocking defaultValue

    val decryptedValue = SecurityUtils.decryptData(
            keyAlias = SECURITY_KEY_ALIAS,
            encryptedData = encryptedValue
    )

    when (defaultValue) {
        is Boolean -> Json.decodeFromString(deserializer = Boolean.serializer(), string = decryptedValue)
        is String -> Json.decodeFromString(deserializer = String.serializer(), string = decryptedValue)
        is Int -> Json.decodeFromString(deserializer = Int.serializer(), string = decryptedValue)
        is Long -> Json.decodeFromString(deserializer = Long.serializer(), string = decryptedValue)
        is Float -> Json.decodeFromString(deserializer = Float.serializer(), string = decryptedValue)
        else -> throw IllegalStateException("Preferences value T can only be Boolean, Int, Long, Float, String, Set<String>.")
    } as T
}

fun <T> Flow<Preferences>.watchSecureSerializedValue(
        key: Preferences.Key<String>,
        defaultValue: T
): Flow<T> = this.map { prefs ->
    val encryptedValue = prefs[key]

    if (encryptedValue.isNullOrBlank())
        return@map defaultValue

    val decryptedValue = SecurityUtils.decryptData(
            keyAlias = SECURITY_KEY_ALIAS,
            encryptedData = encryptedValue
    )

    when (defaultValue) {
        is Boolean -> Json.decodeFromString(deserializer = Boolean.serializer(), string = decryptedValue)
        is String -> Json.decodeFromString(deserializer = String.serializer(), string = decryptedValue)
        is Int -> Json.decodeFromString(deserializer = Int.serializer(), string = decryptedValue)
        is Long -> Json.decodeFromString(deserializer = Long.serializer(), string = decryptedValue)
        is Float -> Json.decodeFromString(deserializer = Float.serializer(), string = decryptedValue)
        else -> throw IllegalStateException("Preferences value T can only be Boolean, Int, Long, Float, String, Set<String>.")
    } as T
}

inline fun <reified T : Serializable> DataStore<Preferences>.setSecureSerializedValue(
        key: Preferences.Key<String>,
        value: T?
) = runBlocking<Unit> {
    edit {
        if (value == null) {
            it.remove(key)
        } else {
            val text = when (value) {
                is Boolean -> Json.encodeToString(serializer = Boolean.serializer(), value = value)
                is String -> Json.encodeToString(serializer = String.serializer(), value = value)
                is Int -> Json.encodeToString(serializer = Int.serializer(), value = value)
                is Long -> Json.encodeToString(serializer = Long.serializer(), value = value)
                is Float -> Json.encodeToString(serializer = Float.serializer(), value = value)
                else -> throw IllegalStateException("Preferences value T can only be Boolean, Int, Long, Float, String, Set<String>.")
            }

            val encryptedValue = SecurityUtils.encryptData(
                    keyAlias = SECURITY_KEY_ALIAS,
                    text = text
            )

            it[key] = encryptedValue
        }
    }
}

