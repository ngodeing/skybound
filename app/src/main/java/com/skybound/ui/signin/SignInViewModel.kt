package com.skybound.ui.signin

import androidx.lifecycle.ViewModel
import com.skybound.data.user.UserRepository

class SignInViewModel(private val repository: UserRepository) : ViewModel() {
//    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
//    val loginResult: LiveData<Result<LoginResponse>> = _loginResult
//
//    fun login(email: String, password: String) {
//        viewModelScope.launch {
//            try {
//                val response = repository.loginUser(LoginRequest(email, password))
//                val user = UserModel(
//                    email = response.loginResult?.name ?: "",
//                    token = response.loginResult?.token ?: "",
//                    isLogin = true
//                )
//                repository.saveSession(user)
//                _loginResult.postValue(Result.success(response))
//            } catch (e: Exception) {
//                _loginResult.postValue(Result.failure(e))
//            }
//        }
//    }
}