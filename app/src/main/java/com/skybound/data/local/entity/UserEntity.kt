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
    val totalCourses: Int = 0,
    val userStreak: Int = 0,
    val userPercentage: Float = 0f,
    val onCourse: String = "",
    val courseStatus: String = "",
    val deadlineLeft: Int = 0,
    val status: String = "",
    val roadmap: String = ""
)