package com.skybound.ui.aftersignin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skybound.data.user.UserRepository
import kotlinx.coroutines.launch

class AfterSignInViewModel(private val userRepository: UserRepository): ViewModel() {

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}