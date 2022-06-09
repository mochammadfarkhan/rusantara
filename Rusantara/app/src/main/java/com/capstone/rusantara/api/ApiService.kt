package com.capstone.rusantara.api

import com.capstone.rusantara.models.ImageData
import com.capstone.rusantara.models.SignupResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("/signup")
    fun signupUser(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<SignupResponse>

    @Multipart
    @POST("/predict")
    fun predict(
        @Part file: MultipartBody.Part,
        @Header("Authorization") authHeader: String
    ): Call<ImageData>

    @GET("/foods/{search}")
    fun getAllFoods(
        @Path("search") search: String,
        @Header("Authorization") authHeader: String
    ): Call<List<ImageData>>
}