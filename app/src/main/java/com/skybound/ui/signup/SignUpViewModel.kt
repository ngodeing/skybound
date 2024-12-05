package com.skybound.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skybound.data.remote.response.RegisterRequest
import com.skybound.data.remote.response.RegisterResponse
import com.skybound.data.user.UserRepository
import kotlinx.coroutines.launch

class SignUpViewModel(private val repository: UserRepository) : ViewModel() {

    private val _signupResult = MutableLiveData<Result<RegisterResponse>>()
    val signupResult: LiveData<Result<RegisterResponse>> = _signupResult

    fun signup(name: String, email: String, password: String, phoneNumber: String, dateOfBirth: String, gender: String, status: String) {
        viewModelScope.launch {
            try {
                val response = repository.signupUser(RegisterRequest(name, email, phoneNumber, dateOfBirth, gender, password, status))
                _signupResult.postValue(Result.success(response))
            } catch (e: Exception) {
                _signupResult.postValue(Result.failure(e))
            }
        }
    }
}