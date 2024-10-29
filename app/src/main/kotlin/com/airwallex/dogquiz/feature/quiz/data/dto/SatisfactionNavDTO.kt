package com.airwallex.dogquiz.feature.quiz.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class SatisfactionNavDTO(
    val isSuccess: Boolean,
    val imageUrl: String,
    val breedName: String,
)
