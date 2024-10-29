package com.airwallex.core.util.json

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.EnumJsonAdapter
import com.airwallex.core.util.tryOrNull

inline fun <reified T : Enum<T>> Moshi.Builder.addEnumAdapterWithFallback(fallbackValue: T?): Moshi.Builder =
    add(T::class.java, EnumJsonAdapter.create(T::class.java).withUnknownFallback(fallbackValue))

inline fun <reified T> Moshi.fromJson(json: String): T? =
    tryOrNull(shouldLogException = true) {
        this.adapter(T::class.java).fromJson(json)
    }
