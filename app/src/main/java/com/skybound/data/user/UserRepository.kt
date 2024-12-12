package com.skybound.data.user

import com.skybound.data.Roadmap2Item
import com.skybound.data.local.dao.UserDao
import com.skybound.data.local.entity.UserEntity
import com.skybound.data.remote.response.Course
import com.skybound.data.remote.response.GenerateQuestionRequest
import com.skybound.data.remote.response.GeneratedQuestion
import com.skybound.data.remote.response.LoginRequest
import com.skybound.data.remote.response.LoginResponse
import com.skybound.data.remote.response.PointsRequest
import com.skybound.data.remote.response.PointsResponse
import com.skybound.data.remote.response.RegisterRequest
import com.skybound.data.remote.response.RegisterResponse
import com.skybound.data.remote.response.Roadmap2Request
import com.skybound.data.remote.response.Roadmap2Response
import com.skybound.data.remote.response.SubCourseResponse
import com.skybound.data.remote.response.UserResponse
import com.skybound.data.remote.response.UserStatusResponse
import com.skybound.data.remote.retrofit.ApiConfig
import com.skybound.ui.settings.SettingPreferences
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val settingPreferences: SettingPreferences,
    private val userDao: UserDao
) {

    suspend fun saveSession(user: User) {
        userPreference.saveSession(user)
    }

    suspend fun saveRoadmap(roadmap2Item: Roadmap2Item) {
        userPreference.saveRoadmap(roadmap2Item)
    }

    fun getSession(): Flow<User> {
        return userPreference.getSession()
    }

    fun getRoadmap(): Flow<Roadmap2Item> {
        return userPreference.getRoadmap()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun signupUser(request: RegisterRequest): RegisterResponse {
        val apiService = ApiConfig.getApiService()
        val response = apiService.registerUser(request)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Signup failed: Empty response")
        } else {
            throw Exception("Signup failed: ${response.message()}")
        }
    }

    suspend fun loginUser(request: LoginRequest): LoginResponse {
        val apiService = ApiConfig.getApiService()
        val response = apiService.loginUser(request)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Login failed: Empty response")
        } else {
            throw Exception("Login failed: ${response.message()}")
        }
    }

    suspend fun saveRoadmap(token: String, request: Roadmap2Request): Roadmap2Response {
        val apiService = ApiConfig.getApiService()
        val response = apiService.saveRoadmap2("Bearer $token", request)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Save roadmap failed: Empty response")
        } else {
            throw Exception("Save roadmap failed: ${response.message()}")
        }
    }

    suspend fun getUserStatus(token: String): UserStatusResponse {
        val apiService = ApiConfig.getApiService()
        val response = apiService.getUserStatus("Bearer $token")
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Failed to fetch user status: Empty response")
        } else {
            throw Exception("Failed to fetch user status: ${response.message()}")
        }
    }

    suspend fun saveUserToDatabase(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun getUserFromDatabase(userId: String): UserEntity? {
        return userDao.getUserById(userId)
    }

    suspend fun deleteUserFromDatabase(userId: String) {
        userDao.deleteUserById(userId)
    }

    suspend fun getUser(token: String): UserResponse {
        val apiService = ApiConfig.getApiService()
        val response = apiService.getUser("Bearer $token")
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Gagal mengambil data pengguna: Respon kosong")
        } else {
            throw Exception("Gagal mengambil data pengguna: ${response.message()}")
        }
    }

    suspend fun getCourses(token: String): List<Course> {
        val apiService = ApiConfig.getApiService()
        val response = apiService.getCourses("Bearer $token")
        if (response.isSuccessful) {
            return response.body()?.courses ?: emptyList()
        } else {
            throw Exception("Failed to fetch courses: ${response.message()}")
        }
    }

    suspend fun getSubCourses(token: String, roadmapName: String, courseName: String): SubCourseResponse {
        val apiService = ApiConfig.getApiService()
        val response = apiService.getSubCourses("Bearer $token", roadmapName, courseName)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Failed to fetch subcourses: Empty response")
        } else {
            throw Exception("Failed to fetch subcourses: ${response.message()}")
        }
    }

    suspend fun generateQuestions(text: String): List<GeneratedQuestion> {
        val apiService = ApiConfig.getSecondaryApiService()
        val request = GenerateQuestionRequest(text)
        val response = apiService.generateQuestion(request)
        if (response.isSuccessful) {
            return response.body()?.generatedQuestions ?: emptyList()
        } else {
            throw Exception("Failed to generate questions: ${response.message()}")
        }
    }

    suspend fun submitPoints(points: Int, courseName: String, token: String): PointsResponse {
        val apiService = ApiConfig.getApiService()
        val request = PointsRequest(points = points.toString(), courseName = courseName)
        val response = apiService.submitPoints("Bearer $token",request)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty response from server")
        } else {
            throw Exception("Failed to submit points: ${response.message()}")
        }
    }




    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            settingPreferences: SettingPreferences,
            userDao: UserDao
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, settingPreferences, userDao)
            }.also { instance = it }
    }
}