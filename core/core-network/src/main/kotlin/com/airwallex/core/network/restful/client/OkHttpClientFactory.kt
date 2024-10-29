package com.airwallex.core.network.restful.client

import com.airwallex.core.network.restful.interceptor.addAppMetadataRequestInterceptor
import com.airwallex.core.network.restful.interceptor.addLoggingInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

interface OkHttpClientFactory {
    fun createOkHttpClient(config: OkHttpClientConfig): OkHttpClient

    fun OkHttpClientConfig.newBasicOkHttpClientBuilder() =
        OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addAppMetadataRequestInterceptor(userAgent, deviceIdResolver, applicationId, appVersionName)
            .addLoggingInterceptor(isProduction)
}

data class OkHttpClientConfig(
    val isProduction: Boolean,
    val userAgent: String,
    val deviceIdResolver: () -> String,
    val applicationId: String,
    val appVersionName: String,
)
