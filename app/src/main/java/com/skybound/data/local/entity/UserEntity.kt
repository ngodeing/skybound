package com.skybound.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey val id: String,
    val username: String,
    val email: String,
    val phoneNumber: String,
    val dateOfBirth: String,
    val gender: String,
    val userPoint: Int,
    val onCourse: String?,
    val courseStatus: String,
    val deadlineLeft: String?
)