package com.airwallex.dogquiz.di

import android.content.Context
import com.airwallex.core.network.di.PublicOkHttpClientConfig
import com.airwallex.core.network.restful.client.OkHttpClientConfig
import com.airwallex.core.util.deviceIdentifier
import com.airwallex.core.util.osVersion
import com.airwallex.dogquiz.BuildConfig
import com.airwallex.dogquiz.data.datastore.AppPreferencesDataStore
import com.airwallex.dogquiz.util.isProductionRelease
import com.airwallex.dogquiz.util.isSandbox
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val basicUserAgent: String
        get() {
            val envStr = when {
                isSandbox -> " SANDBOX"
                else -> ""
            }
            val appVersionStr = BuildConfig.VERSION_NAME
            val phoneInfoStr = deviceIdentifier
            val osStr = "Android/${osVersion}"

            return "Airwallex${envStr}/${appVersionStr} $phoneInfoStr $osStr"
        }

    private val okHttpClientUserAgent get() = "$basicUserAgent OkHttpClient"

    @Provides
    @Singleton
    @PublicOkHttpClientConfig
    fun providePublicOkHttpClientConfig(
        @ApplicationContext ctx: Context,
        appPreferencesDataStore: AppPreferencesDataStore
    ): OkHttpClientConfig = OkHttpClientConfig(
        isProduction = isProductionRelease,
        userAgent = okHttpClientUserAgent,
        deviceIdResolver = { appPreferencesDataStore.deviceID },
        applicationId = BuildConfig.APPLICATION_ID,
        appVersionName = BuildConfig.VERSION_NAME
    )

}
