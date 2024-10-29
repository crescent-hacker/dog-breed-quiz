package com.airwallex.dogquiz.feature.quiz.di

import com.airwallex.core.util.EventBus
import com.airwallex.dogquiz.feature.quiz.data.repository.DogBreedRepository
import com.airwallex.dogquiz.feature.quiz.network.api.DogBreedApi
import com.airwallex.feature.shared.data.model.dto.AppEvent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideDogBreedRepository(
        eventBus: EventBus<AppEvent>,
        dogBreedApi: DogBreedApi
    ): DogBreedRepository =
        DogBreedRepository(eventBus, dogBreedApi)
}
