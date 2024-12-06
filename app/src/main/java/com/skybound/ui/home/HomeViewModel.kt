package com.skybound.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skybound.data.local.entity.UserEntity
import com.skybound.data.remote.response.UserStatusResponse
import com.skybound.data.user.User
import com.skybound.data.user.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _userStatus = MutableLiveData<UserStatusResponse>()
    val userStatus: LiveData<UserStatusResponse> = _userStatus

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getSession(): Flow<User> {
        return userRepository.getSession()
    }

    fun saveUserLocally(user: UserEntity) {
        viewModelScope.launch {
            try {
                userRepository.saveUserToDatabase(user)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun getUserLocally(userId: String) {
        viewModelScope.launch {
            try {
                val user = userRepository.getUserFromDatabase(userId)
                _userStatus.value = user?.let {
                    UserStatusResponse(
                        username = it.username,
                        userPoint = it.userPoint,
                        courseStatus = it.courseStatus,
                        onCourse = it.onCourse ?: "",
                        userStreak = 0,
                        coursesLeft = 0,
                        deadlineLeft = it.deadlineLeft
                    )
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }


    fun fetchUserStatus(token: String) {
        viewModelScope.launch {
            try {
                val status = userRepository.getUserStatus(token)
                _userStatus.value = status
            } catch (e: Exception) {
                _error.value = "Gagal mengambil data dari API, mencoba memuat data lokal."
                try {
                    val user = userRepository.getUserFromDatabase("user_id_example")
                    _userStatus.value = user?.let {
                        UserStatusResponse(
                            username = it.username,
                            userPoint = it.userPoint,
                            courseStatus = it.courseStatus,
                            onCourse = it.onCourse ?: "",
                            userStreak = 0,
                            coursesLeft = 0,
                            deadlineLeft = it.deadlineLeft
                        )
                    }
                } catch (localError: Exception) {
                    _error.value = "Gagal memuat data lokal: ${localError.message}"
                }
            }
        }
    }
}
