package com.skybound

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.skybound.data.Roadmap2Item
import com.skybound.data.remote.response.UserResponse
import com.skybound.data.user.User
import com.skybound.data.user.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage


    private val _userResponse = MutableLiveData<UserResponse>()
    val userResponse: LiveData<UserResponse> get() = _userResponse

    fun getSession(): LiveData<User> {
        return userRepository.getSession().asLiveData()
    }

    fun fetchUser(token: String) {
        viewModelScope.launch {
            try {
                val user = userRepository.getUser(token)
                _userResponse.value = user
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching user data: ${e.message}")
            }
        }
    }
}
