package com.airwallex.dogquiz.feature.quiz.di

import android.content.Context
import com.airwallex.core.network.di.PublicRetrofitFactory
import com.airwallex.core.network.restful.client.RetrofitFactory
import com.airwallex.dogquiz.R
import com.airwallex.dogquiz.feature.quiz.network.api.DogBreedApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    @Singleton
    @DogBreedMoshi
    fun provideDogBreedMoshi(): Moshi =
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    @DogBreedRetrofit
    fun provideDogBreedRetrofit(
        @PublicRetrofitFactory retrofitFactory: RetrofitFactory,
        @DogBreedMoshi moshi: Moshi,
        @ApplicationContext ctx: Context
    ): Retrofit =
        retrofitFactory.createRetrofit(
            baseUrl = ctx.getString(R.string.dog_ceo_api_host),
            moshi = moshi,
        )

    @Provides
    @Singleton
    fun provideDogBreedApi(
        @DogBreedRetrofit retrofit: Retrofit
    ): DogBreedApi =
        retrofit.create(DogBreedApi::class.java)
}
