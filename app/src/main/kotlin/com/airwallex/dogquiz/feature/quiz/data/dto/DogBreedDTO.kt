package com.airwallex.dogquiz.feature.quiz.data.dto

import com.airwallex.core.util.titleCase

data class DogBreedDTO(
    val breed: String = "",
    val subBreed: String? = null,
    val imageUrl: String? = null
) {
    val displayName: String
        get() = subBreed?.let { "${subBreed.titleCase()} ${breed.titleCase()}" } ?: breed.titleCase()

    companion object {
        val EMPTY = DogBreedDTO()
    }
}

data class DogBreedsDTO(
    val breeds: List<DogBreedDTO>
)
