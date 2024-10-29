package com.airwallex.core.util

inline fun <T> tryOrNull(shouldLogException: Boolean = false, crossinline block: () -> T): T? =
        try {
            block()
        } catch (e: Exception) {
            if (shouldLogException) AppLogger.nonFatalError(e)
            null
        }

