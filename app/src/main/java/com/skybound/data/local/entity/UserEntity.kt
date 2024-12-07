package com.skybound.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey val id: String,
    val username: String,
    val email: String = "",
    val phoneNumber: String = "",
    val dateOfBirth: String = "",
    val gender: String = "",
    val userPoint: Int = 0,
    val userStreak: Int = 0,
    val userPercentage: Int = 0,
    val onCourse: String = "",
    val courseStatus: String = "",
    val deadlineLeft: String? = "",
    val status: String = ""
)