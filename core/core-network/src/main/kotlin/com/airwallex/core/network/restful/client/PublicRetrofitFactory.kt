package com.airwallex.core.network.restful.client

import com.squareup.moshi.Moshi
import com.airwallex.core.network.util.build
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

internal class PublicRetrofitFactory @Inject constructor(
    private val publicOkHttpClient: OkHttpClient
) : RetrofitFactory {
    override fun createRetrofit(
        baseUrl: String,
        moshi: Moshi,
        vararg interceptors: Interceptor
    ): Retrofit =
        Retrofit.Builder()
            .client(
                publicOkHttpClient.newBuilder()
                    .apply {
                        for (interceptor in interceptors) {
                            addInterceptor(interceptor)
                        }
                    }
                    .build(reorderInterceptors = true)
            )
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
}
