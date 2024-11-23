package com.skybound.data.user

data class User(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)