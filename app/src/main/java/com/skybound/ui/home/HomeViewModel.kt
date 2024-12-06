package com.skybound.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun fetchUserStatus(token: String) {
        viewModelScope.launch {
            try {
                val status = userRepository.getUserStatus(token)
                _userStatus.value = status
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
