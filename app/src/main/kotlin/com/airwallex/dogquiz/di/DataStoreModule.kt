package com.airwallex.dogquiz.di

import android.content.Context
import com.airwallex.dogquiz.data.datastore.AppPreferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideAppPreferencesDataStore(
            @ApplicationContext ctx: Context
    ): AppPreferencesDataStore = AppPreferencesDataStore(ctx)
}
