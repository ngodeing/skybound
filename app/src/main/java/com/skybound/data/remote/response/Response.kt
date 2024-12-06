package com.skybound.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String,

	@field:SerializedName("dateOfBirth")
	val dateOfBirth: String,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("userPoint")
	val userPoint: String = "",

	@field:SerializedName("roadmaps")
	val roadmaps: List<Any?>? = null,

	@field:SerializedName("completedCourses")
	val completedCourses: Int = 0,

	@field:SerializedName("userStreak")
	val userStreak: Int = 0,

	@field:SerializedName("onCourse")
	val onCourse: String? = null,

	@field:SerializedName("courseStatus")
	val courseStatus: String = "Inactive",

	@field:SerializedName("totalCourses")
	val totalCourses: Int = 0,

	@field:SerializedName("deadline")
	val deadline: String? = null
)

data class RegisterResponse(
	@field:SerializedName("message")
	val message: String
)

data class LoginRequest(
	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("password")
	val password: String
)

data class LoginResponse(
	@field:SerializedName("token")
	val token: String,

	@field:SerializedName("message")
	val message: String
)
data class UserStatusResponse(
	@SerializedName("username") val username: String,
	@SerializedName("userPoint") val userPoint: Int,
	@SerializedName("courseStatus") val courseStatus: String,
	@SerializedName("onCourse") val onCourse: String,
	@SerializedName("userStreak") val userStreak: Int,
	@SerializedName("coursesLeft") val coursesLeft: Int,
	@SerializedName("deadlineLeft") val deadlineLeft: String?
)