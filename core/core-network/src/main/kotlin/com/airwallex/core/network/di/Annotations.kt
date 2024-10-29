package com.airwallex.core.network.di

import javax.inject.Qualifier

@Qualifier
@Retention
annotation class PublicOkHttpClient

@Qualifier
@Retention
annotation class PublicOkHttpClientConfig

@Qualifier
@Retention
annotation class PublicRetrofitFactory
