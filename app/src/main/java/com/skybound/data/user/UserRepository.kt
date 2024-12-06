package com.skybound.data.user

import com.skybound.data.local.dao.UserDao
import com.skybound.data.local.entity.UserEntity
import com.skybound.data.remote.response.LoginRequest
import com.skybound.data.remote.response.LoginResponse
import com.skybound.data.remote.response.RegisterRequest
import com.skybound.data.remote.response.RegisterResponse
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

    fun getSession(): Flow<User> {
        return userPreference.getSession()
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