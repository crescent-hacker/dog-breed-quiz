package com.airwallex.dogquiz.feature.quiz.data.repository

import com.airwallex.core.network.util.requireBody
import com.airwallex.core.network.util.restfulApiFlow
import com.airwallex.core.util.EventBus
import com.airwallex.dogquiz.feature.quiz.data.dto.DogBreedDTO
import com.airwallex.dogquiz.feature.quiz.data.dto.DogBreedsDTO
import com.airwallex.dogquiz.feature.quiz.data.model.DogBreedRandomImageResultMapper
import com.airwallex.dogquiz.feature.quiz.data.model.DogBreedsResultMapper
import com.airwallex.dogquiz.feature.quiz.network.api.DogBreedApi
import com.airwallex.feature.shared.data.model.dto.AppEvent
import com.airwallex.feature.shared.data.repository.base.PublicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class DogBreedRepository @Inject constructor(
    eventBus: EventBus<AppEvent>,
    private val dogBreedApi: DogBreedApi
) : PublicRepository(eventBus) {
    private val dogBreedsCache = cache {
        runBlocking(scope.coroutineContext) {
            restfulApiFlow { dogBreedApi.getDogBreeds() }
                .requireBody()
                .map(DogBreedsResultMapper::map)
        }
    }

    fun getDogBreeds(refresh: Boolean = false): Flow<Result<DogBreedsDTO>> =
        dogBreedsCache.getAsFlow(refresh)

    suspend fun getDogBreedRandomImage(breedDTO: DogBreedDTO): Flow<Result<String>> =
        runBlocking(scope.coroutineContext) {
            restfulApiFlow {
                if (breedDTO.subBreed != null) {
                    dogBreedApi.getDogSubBreedRandomImage(breedDTO.breed, breedDTO.subBreed)
                } else {
                    dogBreedApi.getDogBreedRandomImage(breedDTO.breed)
                }
            }
                .requireBody()
                .map(DogBreedRandomImageResultMapper::map)
        }
}

