package com.airwallex.feature.shared.di

import android.content.Context
import androidx.compose.runtime.compositionLocalOf
import com.airwallex.core.util.AirwallexLogger
import com.airwallex.core.util.AppLogger
import com.airwallex.core.util.EventBus
import com.airwallex.feature.shared.data.model.dto.AppEvent
import com.airwallex.feature.shared.service.FirebaseService
import com.airwallex.feature.shared.service.FirebaseServiceImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedServiceModule {
    @Provides
    @Singleton
    fun provideAppEventBus(): EventBus<AppEvent> = EventBus()

    @Provides
    @Singleton
    fun provideLogger(): AirwallexLogger = AppLogger

    @Provides
    @Singleton
    fun provideFirebaseService(
        @ApplicationContext ctx: Context,
    ): FirebaseService = FirebaseServiceImpl(ctx)

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

}

val LocalAppEventBus = compositionLocalOf<EventBus<AppEvent>> {
    error("LocalAppEventBus is not provided.")
}

val LocalAnalyticsPage = compositionLocalOf<String> {
    error("LocalAnalyticsPage is not provided.")
}

val LocalMoshiInstance = compositionLocalOf<Moshi> {
    error("LocalMoshiInstance is not provided.")
}
