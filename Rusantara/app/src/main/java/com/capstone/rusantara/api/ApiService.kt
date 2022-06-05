package com.capstone.rusantara.api

import com.capstone.rusantara.models.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

interface ApiService {
    @POST("signup")
    fun signupUser(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<SignupResponse>


}