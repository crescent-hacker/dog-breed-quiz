package com.airwallex.core.network.di

import com.airwallex.core.network.restful.client.RetrofitFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton
import com.airwallex.core.network.restful.client.PublicRetrofitFactory as PublicRetrofitFactoryImpl

@Module
@InstallIn(SingletonComponent::class)
object RetrofitFactoryModule {
    @Provides
    @Singleton
    @PublicRetrofitFactory
    fun providePublicRetrofitFactory(
        @PublicOkHttpClient okHttpClient: OkHttpClient,
    ): RetrofitFactory =
        PublicRetrofitFactoryImpl(okHttpClient)
}
