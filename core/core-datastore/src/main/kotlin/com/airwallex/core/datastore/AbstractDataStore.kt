package com.airwallex.core.datastore

abstract class AbstractDataStore {
    abstract suspend fun clearAll()
}
