package com.skybound.ui.signup

import androidx.lifecycle.ViewModel
import com.skybound.data.user.UserRepository

class SignUpViewModel(private val repository: UserRepository) : ViewModel() {

//    private val _signupResult = MutableLiveData<Result<RegisterResponse>>()
//    val signupResult: LiveData<Result<RegisterResponse>> = _signupResult
//
//    fun signup(name: String, email: String, password: String) {
//        viewModelScope.launch {
//            try {
//                val response = repository.signupUser(RegisterRequest(name, email, password))
//                _signupResult.postValue(Result.success(response))
//            } catch (e: Exception) {
//                _signupResult.postValue(Result.failure(e))
//            }
//        }
//    }
}