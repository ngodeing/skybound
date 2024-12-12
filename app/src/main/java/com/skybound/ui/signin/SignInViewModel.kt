package com.skybound.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skybound.data.Roadmap2Item
import com.skybound.data.remote.response.LoginRequest
import com.skybound.data.remote.response.LoginResponse
import com.skybound.data.remote.response.RequestOTPRequest
import com.skybound.data.remote.response.RequestOTPResponse
import com.skybound.data.remote.response.VerifyOTPRequest
import com.skybound.data.remote.response.VerifyOTPResponse
import com.skybound.data.user.User
import com.skybound.data.user.UserRepository
import kotlinx.coroutines.launch

class SignInViewModel(private val repository: UserRepository) : ViewModel() {
    private val _otpRequestResult = MutableLiveData<Result<RequestOTPResponse>>()
    val otpRequestResult: LiveData<Result<RequestOTPResponse>> = _otpRequestResult

    private val _otpVerifyResult = MutableLiveData<Result<VerifyOTPResponse>>()
    val otpVerifyResult: LiveData<Result<VerifyOTPResponse>> = _otpVerifyResult

    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private lateinit var verifyUser: User

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun login(email: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.loginUser(LoginRequest(email, password))
                val user = User(
                    token = response.token,
                    isLogin = true
                )

                repository.saveSession(user)

                try {
                    val getuser = repository.getUser(user.token)
                    val roadmap2item = Roadmap2Item(
                        title = getuser.roadmaps,
                        date = "20-12-2026",
                        isRoadmap2 = true
                    )
                repository.saveRoadmap(roadmap2item)
                } catch (e: Exception) {
                    _error.value = "Failed Fetch User"
                }

                _loginResult.postValue(Result.success(response))
            } catch (e: Exception) {
                _loginResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

//    fun requestOTP(email: String, password: String) {
//        viewModelScope.launch {
//            try {
//                val response = repository.loginUser(LoginRequest(email, password))
//                val user = User(
//                    token = response.token,
//                    isLogin = true
//                )
//                try {
//                    val getuser = repository.getUser(user.token)
//                    val roadmap2item = Roadmap2Item(
//                        title = getuser.roadmaps,
//                        date = "20-12-2026",
//                        isRoadmap2 = true
//                    )
//                    repository.saveRoadmap(roadmap2item)
//                    try {
//                        val response2 = repository.requestOTP(user.token, RequestOTPRequest(email))
//                        _otpRequestResult.postValue(Result.success(response2))
//                        verifyUser = user
//
//                    } catch (e: Exception) {
//                        _otpRequestResult.postValue(Result.failure(e))
//                    }
//                } catch (e: Exception) {
//                    _error.value = "Failed Fetch User"
//                }
//                _loginResult.postValue(Result.success(response))
//            } catch (e: Exception) {
//                _loginResult.value = Result.failure(e)
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
//
//    fun verifyOTP(email: String, otp: String) {
//        viewModelScope.launch {
//            try {
//                val response = repository.verifyOTP(VerifyOTPRequest(email, otp))
//                _otpVerifyResult.postValue(Result.success(response))
//                repository.saveSession(verifyUser)
//            } catch (e: Exception) {
//                _otpVerifyResult.postValue(Result.failure(e))
//            }
//        }
//    }
//
//    fun logout() {
//        viewModelScope.launch {
//            repository.logout()
//            verifyUser = User(
//                token = "",
//                isLogin = false
//            )
//        }
//    }
}

