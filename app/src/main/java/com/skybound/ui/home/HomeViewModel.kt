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

    fun mergeAndSaveUserLocally(newUser: UserEntity) {
        viewModelScope.launch {
            try {
                val currentUser = userRepository.getUserFromDatabase(newUser.id)
                val mergedUser = currentUser?.copy(
                    username = if (newUser.username.isNotBlank()) newUser.username else currentUser.username,
                    email = if (newUser.email.isNotBlank()) newUser.email else currentUser.email,
                    phoneNumber = if (newUser.phoneNumber.isNotBlank()) newUser.phoneNumber else currentUser.phoneNumber,
                    dateOfBirth = if (newUser.dateOfBirth.isNotBlank()) newUser.dateOfBirth else currentUser.dateOfBirth,
                    userStreak = if (newUser.userStreak != 0) newUser.userStreak else currentUser.userStreak,
                    userPercentage = if (newUser.userPercentage != 0f) newUser.userPercentage else currentUser.userPercentage,
                    gender = if (newUser.gender.isNotBlank()) newUser.gender else currentUser.gender,
                    userPoint = if (newUser.userPoint != 0) newUser.userPoint else currentUser.userPoint,
                    onCourse = if (newUser.onCourse.isNotBlank()) newUser.onCourse else currentUser.onCourse,
                    courseStatus = if (newUser.courseStatus.isNotBlank()) newUser.courseStatus else currentUser.courseStatus,
                    deadlineLeft = newUser.deadlineLeft ?: currentUser.deadlineLeft,
                    status = if (newUser.status.isNotBlank()) newUser.status else currentUser.status,
                    roadmap = if (newUser.roadmap.isNotBlank()) newUser.roadmap else currentUser.roadmap
                ) ?: newUser

                userRepository.saveUserToDatabase(mergedUser)
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
                        onCourse = it.onCourse,
                        userPercentage = it.userPercentage,
                        userStreak = 0,
                        coursesLeft = 0,
                        deadlineLeft = it.deadlineLeft,
                        roadmaps = it.roadmap,
                        totalCourses = it.totalCourses
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
                _error.value = "Failed Fetch, Try Access local data."
                try {
                    val user = userRepository.getUserFromDatabase("user_id_example")
                    _userStatus.value = user?.let {
                        UserStatusResponse(
                            username = it.username,
                            userPoint = it.userPoint,
                            courseStatus = it.courseStatus,
                            onCourse = it.onCourse,
                            userPercentage = it.userPercentage,
                            userStreak = 0,
                            coursesLeft = 0,
                            deadlineLeft = it.deadlineLeft,
                            roadmaps = it.roadmap,
                            totalCourses = it.totalCourses
                        )
                    }
                } catch (localError: Exception) {
                    _error.value = "Gagal memuat data lokal: ${localError.message}"
                }
            }
        }
    }
}
