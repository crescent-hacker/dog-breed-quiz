package com.airwallex.dogquiz.di

import android.content.Context
import com.airwallex.dogquiz.main.AppEventProcessor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object ActivityComponentModule {

    @Provides
    @ActivityScoped
    fun provideAppEventProcessor(
        @ActivityContext ctx: Context,
    ): AppEventProcessor = AppEventProcessor(ctx)
}


