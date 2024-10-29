package com.airwallex.core.datastore.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import com.airwallex.core.datastore.AbstractDataStore
import java.io.IOException

abstract class PreferencesDataStore(
        val context: Context,
        dataStoreName: String
) : AbstractDataStore() {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = dataStoreName)

    override suspend fun clearAll() {
        context.dataStore.edit { it.clear() }
    }

    /**
     *  Helper functions
     */
    protected fun <T> Preferences.Key<T>.watchValue(defaultValue: T): Flow<T> {
        return context.dataStore.data
                .catchAndHandleError()
                .map { preferences -> preferences[this] ?: defaultValue }
    }

    protected fun <T> Preferences.Key<T>.watchValue(): Flow<T?> {
        return context.dataStore.data
                .catchAndHandleError()
                .map { preferences -> preferences[this] }
    }

    protected fun <T> Preferences.Key<String>.watchSecureValue(defaultValue: T): Flow<T> {
        return context.dataStore.data
                .catchAndHandleError()
                .watchSecureSerializedValue(this, defaultValue)
    }

    protected fun <T, M> Preferences.Key<T>.watchValue(convertToValueFunction: (T?) -> M?): Flow<M?> {
        return context.dataStore.data
                .catchAndHandleError()
                .map { preferences -> preferences[this] }
                .map(convertToValueFunction)
    }

    protected suspend fun <T> Preferences.Key<T>.getValue(defaultValue: T): T {
        return context.dataStore.data
                .catchAndHandleError()
                .map { preferences -> preferences[this] }
                .firstOrNull() ?: defaultValue
    }

    protected suspend fun <T> Preferences.Key<T>.getValue(): T? {
        return context.dataStore.data
                .catchAndHandleError()
                .map { preferences -> preferences[this] }
                .firstOrNull()
    }

    protected suspend fun <T, M> Preferences.Key<T>.getValue(map: (T?) -> M?): M? {
        return context.dataStore.data
                .catchAndHandleError()
                .map { preferences -> map(preferences[this]) }
                .firstOrNull()
    }

    protected suspend fun <T> Preferences.Key<T>.setValue(value: T) {
        context.dataStore.edit { preferences -> preferences[this] = value }
    }

    private fun Flow<Preferences>.catchAndHandleError(): Flow<Preferences> {
        this.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        return this@catchAndHandleError
    }
}
