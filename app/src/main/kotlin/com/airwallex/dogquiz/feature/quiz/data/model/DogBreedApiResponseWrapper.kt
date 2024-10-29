package com.airwallex.dogquiz.feature.quiz.data.model

import com.airwallex.core.network.util.ResultMapper
import com.airwallex.dogquiz.feature.quiz.data.dto.DogBreedDTO
import com.airwallex.dogquiz.feature.quiz.data.dto.DogBreedsDTO
import kotlinx.serialization.Serializable

@Serializable
data class DogBreedApiResponseWrapper<T>(
    val message: T,
    val status: String
)

typealias DogBreedsResponse = DogBreedApiResponseWrapper<Map<String, List<String>>>
typealias DogBreedRandomImageResponse = DogBreedApiResponseWrapper<String>

val DogBreedsResultMapper = ResultMapper<DogBreedsResponse, DogBreedsDTO> { response ->
    if (response.isSuccess) {
        Result.success(
            DogBreedsDTO(
                breeds = response.getOrThrow().message.flatMap { (breed, subBreeds) ->
                    subBreeds.map { DogBreedDTO(breed = breed, subBreed = it) }
                }
            )
        )
    } else {
        Result.failure(response.exceptionOrNull()!!)
    }
}

val DogBreedRandomImageResultMapper = ResultMapper<DogBreedRandomImageResponse, String> { response ->
    if (response.isSuccess) {
        Result.success(response.getOrThrow().message)
    } else {
        Result.failure(response.exceptionOrNull()!!)
    }
}
