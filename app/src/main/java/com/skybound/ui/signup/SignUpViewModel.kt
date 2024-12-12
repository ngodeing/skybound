package com.skybound.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skybound.data.Roadmap2Item
import com.skybound.data.remote.response.LoginRequest
import com.skybound.data.remote.response.LoginResponse
import com.skybound.data.remote.response.RegisterRequest
import com.skybound.data.remote.response.RegisterResponse
import com.skybound.data.remote.response.RequestOTPRequest
import com.skybound.data.remote.response.RequestOTPResponse
import com.skybound.data.remote.response.VerifyOTPRequest
import com.skybound.data.remote.response.VerifyOTPResponse
import com.skybound.data.user.User
import com.skybound.data.user.UserRepository
import kotlinx.coroutines.launch

class SignUpViewModel(private val repository: UserRepository) : ViewModel() {

    private val _signupResult = MutableLiveData<Result<RegisterResponse>>()
    val signupResult: LiveData<Result<RegisterResponse>> = _signupResult

    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    private val _otpRequestResult = MutableLiveData<Result<RequestOTPResponse>>()
    val otpRequestResult: LiveData<Result<RequestOTPResponse>> = _otpRequestResult

    private val _otpVerifyResult = MutableLiveData<Result<VerifyOTPResponse>>()
    val otpVerifyResult: LiveData<Result<VerifyOTPResponse>> = _otpVerifyResult

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

//    fun signup(name: String, email: String, password: String, phoneNumber: String, dateOfBirth: String, gender: String, status: String) {
//        viewModelScope.launch {
//            try {
//                val response = repository.signupUser(RegisterRequest(name, email, phoneNumber, dateOfBirth, gender, password, status))
//                _signupResult.postValue(Result.success(response))
//            } catch (e: Exception) {
//                _signupResult.postValue(Result.failure(e))
//            }
//        }
//    }

    fun requestOTP(email: String) {
        viewModelScope.launch {
            try {
                val response = repository.requestOTP(RequestOTPRequest(email))
                _otpRequestResult.postValue(Result.success(response))
            } catch (e: Exception) {
                _otpRequestResult.postValue(Result.failure(e))
            }
        }
    }

    // Step 2: Verify OTP
    fun verifyOTP(email: String, otp: String) {
        viewModelScope.launch {
            try {
                val response = repository.verifyOTP(VerifyOTPRequest(email, otp))
                _otpVerifyResult.postValue(Result.success(response))
            } catch (e: Exception) {
                _otpVerifyResult.postValue(Result.failure(e))
            }
        }
    }

    // Step 3: Sign Up (will only occur after OTP verification)
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

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}