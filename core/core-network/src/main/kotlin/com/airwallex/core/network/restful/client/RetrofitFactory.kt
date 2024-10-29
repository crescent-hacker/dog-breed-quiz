package com.airwallex.core.network.restful.client

import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import retrofit2.Retrofit

interface RetrofitFactory {
    fun createRetrofit(
        baseUrl: String,
        moshi: Moshi,
        vararg interceptors: Interceptor
    ): Retrofit
}
