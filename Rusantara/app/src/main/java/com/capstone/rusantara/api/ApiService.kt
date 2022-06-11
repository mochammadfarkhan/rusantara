package com.capstone.rusantara.api

import com.capstone.rusantara.models.ImageData
import com.capstone.rusantara.models.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("/signup")
    fun signupUser(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @Multipart
    @POST("/predict")
    fun predict(
        @Part file: MultipartBody.Part,
        @Header("Authorization") authHeader: String
    ): Call<ImageData>

    @GET("/foods/{search}")
    fun searchFoods(
        @Path("search") search: String,
        @Header("Authorization") authHeader: String
    ): Call<ArrayList<ImageData>>

//    @GET("/foods")
//    fun getAllFoods(
//        @Header("Authorization") authHeader: String
//    ): Call<List<ImageData>>
}