package com.airwallex.core.network.di

import com.airwallex.core.network.restful.client.OkHttpClientConfig
import com.airwallex.core.network.restful.client.OkHttpClientFactory
import com.airwallex.core.network.restful.client.PublicOkHttpClientFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OkHttpClientModule {
    @Provides
    @Singleton
    @PublicOkHttpClient
    fun providePublicOkHttpClient(
        @PublicOkHttpClientConfig config: OkHttpClientConfig,
        factory: OkHttpClientFactory
    ): OkHttpClient =
        factory.createOkHttpClient(config)

    @Provides
    @Singleton
    fun providePublicOkHttpClientFactory(): OkHttpClientFactory = PublicOkHttpClientFactory
}
