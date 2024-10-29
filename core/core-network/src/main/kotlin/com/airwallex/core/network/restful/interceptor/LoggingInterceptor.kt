package com.airwallex.core.network.restful.interceptor

import com.airwallex.core.network.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

fun OkHttpClient.Builder.addLoggingInterceptor(isProduction: Boolean) = apply {
    HttpLoggingInterceptor().apply {
        this.level = when {
            isProduction ->HttpLoggingInterceptor.Level.NONE
            BuildConfig.OKHTTPCLIENT_VERBOSE_LOG_ENABLED -> HttpLoggingInterceptor.Level.BODY
            else -> HttpLoggingInterceptor.Level.BASIC
        }
    }.let(::addInterceptor)
}
