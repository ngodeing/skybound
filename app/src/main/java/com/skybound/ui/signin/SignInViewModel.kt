package com.skybound.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skybound.data.Roadmap2Item
import com.skybound.data.remote.response.LoginRequest
import com.skybound.data.remote.response.LoginResponse
import com.skybound.data.remote.response.RegisterRequest
import com.skybound.data.remote.response.RegisterResponse
import com.skybound.data.user.User
import com.skybound.data.user.UserRepository
import kotlinx.coroutines.launch

class SignInViewModel(private val repository: UserRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

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
}

