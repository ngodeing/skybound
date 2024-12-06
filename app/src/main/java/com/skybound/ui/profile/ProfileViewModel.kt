package com.skybound.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skybound.data.remote.response.UserResponse
import com.skybound.data.remote.response.UserStatusResponse
import com.skybound.data.user.User
import com.skybound.data.user.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userData = MutableLiveData<UserStatusResponse>()
    val userData: LiveData<UserStatusResponse> = _userData

    private val _user = MutableLiveData<UserResponse>()
    val user: LiveData<UserResponse> get() = _user

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun getSession(): Flow<User> {
        return userRepository.getSession()
    }

    fun fetchUser(token: String) {
        viewModelScope.launch {
            try {
                val userData = userRepository.getUser(token)
                _user.postValue(userData)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}
