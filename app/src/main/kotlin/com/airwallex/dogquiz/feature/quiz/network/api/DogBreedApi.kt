package com.airwallex.dogquiz.feature.quiz.network.api

import com.airwallex.dogquiz.feature.quiz.data.model.DogBreedRandomImageResponse
import com.airwallex.dogquiz.feature.quiz.data.model.DogBreedsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DogBreedApi {
    @GET("breeds/list/all")
    suspend fun getDogBreeds(): Response<DogBreedsResponse>

    @GET("breed/{breed}/images/random")
    suspend fun getDogBreedRandomImage(@Path("breed") breed: String): Response<DogBreedRandomImageResponse>

    @GET("breed/{breed}/{subBreed}/images/random")
    suspend fun getDogSubBreedRandomImage(@Path("breed") breed: String, @Path("subBreed") subBreed: String): Response<DogBreedRandomImageResponse>
}
