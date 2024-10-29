package com.airwallex.core.network.restful.client

import okhttp3.OkHttpClient


object PublicOkHttpClientFactory : OkHttpClientFactory {
    override fun createOkHttpClient(config: OkHttpClientConfig): OkHttpClient =
        config.newBasicOkHttpClientBuilder()
            .build()
}
