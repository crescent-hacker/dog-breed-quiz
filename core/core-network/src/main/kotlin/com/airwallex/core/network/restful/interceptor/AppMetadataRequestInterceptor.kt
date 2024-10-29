package com.airwallex.core.network.restful.interceptor

import com.airwallex.core.network.NetworkConstants.HEADER_CLIENT_NAME
import com.airwallex.core.network.NetworkConstants.HEADER_CLIENT_VERSION
import com.airwallex.core.network.NetworkConstants.HEADER_DEVICE_ID
import com.airwallex.core.network.NetworkConstants.HEADER_PLATFORM
import com.airwallex.core.network.NetworkConstants.HEADER_USER_AGENT
import com.airwallex.core.util.platformName
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

class AppMetadataRequestInterceptor(
    private val userAgent: String,
    private val deviceIdResolver: () -> String,
    private val applicationId: String,
    private val appVersion: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder().apply {
            header(HEADER_USER_AGENT, userAgent)
            header(HEADER_DEVICE_ID, deviceIdResolver.invoke())
            header(HEADER_PLATFORM, platformName)
            header(HEADER_CLIENT_NAME, applicationId)
            header(HEADER_CLIENT_VERSION, appVersion)
        }.build()

        return chain.proceed(newRequest)
    }

}

fun OkHttpClient.Builder.addAppMetadataRequestInterceptor(
    userAgent: String,
    deviceIdResolver: () -> String,
    applicationId: String,
    appVersion: String
) = apply {
    addNetworkInterceptor(
        AppMetadataRequestInterceptor(
            userAgent = userAgent,
            deviceIdResolver = deviceIdResolver,
            applicationId = applicationId,
            appVersion = appVersion
        )
    )
}
