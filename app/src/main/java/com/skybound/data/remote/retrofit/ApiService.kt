package com.skybound.data.remote.retrofit

import com.skybound.data.remote.response.LoginRequest
import com.skybound.data.remote.response.LoginResponse
import com.skybound.data.remote.response.RegisterRequest
import com.skybound.data.remote.response.RegisterResponse
import com.skybound.data.remote.response.Roadmap2Request
import com.skybound.data.remote.response.Roadmap2Response
import com.skybound.data.remote.response.UserResponse
import com.skybound.data.remote.response.UserStatusResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("users")
    suspend fun registerUser(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @GET("user/status")
    suspend fun getUserStatus(
        @Header("Authorization") token: String
    ): Response<UserStatusResponse>

    @GET("user")
    suspend fun getUser(
        @Header("Authorization") token: String
    ): Response<UserResponse>

    @POST("user/roadmap")
    suspend fun saveRoadmap2(
        @Header("Authorization") token: String,
        @Body request: Roadmap2Request
    ): Response<Roadmap2Response>

}