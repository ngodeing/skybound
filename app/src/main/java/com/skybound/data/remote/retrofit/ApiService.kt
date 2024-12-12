package com.skybound.data.remote.retrofit

import com.skybound.data.remote.response.CoursesResponse
import com.skybound.data.remote.response.LoginRequest
import com.skybound.data.remote.response.LoginResponse
import com.skybound.data.remote.response.RegisterRequest
import com.skybound.data.remote.response.RegisterResponse
import com.skybound.data.remote.response.RequestOTPRequest
import com.skybound.data.remote.response.RequestOTPResponse
import com.skybound.data.remote.response.Roadmap2Request
import com.skybound.data.remote.response.Roadmap2Response
import com.skybound.data.remote.response.SubCourseResponse
import com.skybound.data.remote.response.UserResponse
import com.skybound.data.remote.response.UserStatusResponse
import com.skybound.data.remote.response.VerifyOTPRequest
import com.skybound.data.remote.response.VerifyOTPResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

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

    @GET("users/courses")
    suspend fun getCourses(
        @Header("Authorization") token: String
    ): Response<CoursesResponse>

    @GET("user/{roadmapName}/{courseName}/subcourses")
    suspend fun getSubCourses(
        @Header("Authorization") token: String,
        @Path("roadmapName") roadmapName: String,
        @Path("courseName") courseName: String
    ): Response<SubCourseResponse>

    @POST("login/requestOTP")
    suspend fun requestOTP(
        @Body request: RequestOTPRequest
    ): Response<RequestOTPResponse>

    @POST("login/verifyOTP")
    suspend fun verifyOTP(
        @Body request: VerifyOTPRequest
    ): Response<VerifyOTPResponse>

}