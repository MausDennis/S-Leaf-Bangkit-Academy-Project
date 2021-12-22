package com.mnhyim.s_leaf.core.data.remote.api

import com.mnhyim.s_leaf.core.data.remote.response.PlantResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("all")
    suspend fun getAllPlants(): List<PlantResponse>

    @GET("random")
    suspend fun getPlant(): List<PlantResponse>

    @FormUrlEncoded
    @POST("upload")
    suspend fun uploadImage(
        @Field("image") image: String,
    ): PlantResponse
}