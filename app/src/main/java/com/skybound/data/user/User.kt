package com.skybound.data.user

data class User(
    val token: String,
    val isLogin: Boolean = false
)