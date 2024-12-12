package com.skybound.data.remote.response

import androidx.room.Embedded
import androidx.room.Relation
import com.google.gson.annotations.SerializedName
import com.skybound.data.local.entity.UserEntity

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

data class Roadmap2Response(
	@field:SerializedName("message")
	val message: String
)

data class Roadmap2Request(
	@field:SerializedName("roadmapName")
	val roadmapName: String,
)

data class RequestOTPResponse(
	@field:SerializedName("message")
	val message: String
)

data class RequestOTPRequest(
	@field:SerializedName("email")
	val email: String
)

data class VerifyOTPResponse(
	@field:SerializedName("message")
	val message: String
)

data class VerifyOTPRequest(
	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("otp")
	val otp: String
)

data class UserStatusResponse(
	@SerializedName("username") val username: String,
	@SerializedName("userPoint") val userPoint: Int,
	@SerializedName("userPercentage") val userPercentage: Float,
	@SerializedName("courseStatus") val courseStatus: String,
	@SerializedName("onCourse") val onCourse: String,
	@SerializedName("totalCourses") val totalCourses: Int,
	@SerializedName("userStreak") val userStreak: Int,
	@SerializedName("coursesLeft") val coursesLeft: Int,
	@SerializedName("deadlineLeft") val deadlineLeft: Int,
	@SerializedName("roadmaps") val roadmaps: String
)

data class UserResponse(
	@SerializedName("username") val username: String,
	@SerializedName("email") val email: String,
	@SerializedName("status") val status: String,
	@SerializedName("gender") val gender: String,
	@SerializedName("phoneNumber") val phoneNumber: String,
	@SerializedName("dateOfBirth") val dateOfBirth: String,
	@SerializedName("userPoint") val userPoint: Int,
	@SerializedName("roadmaps") val roadmaps: String
)

data class RoadmapResponse(
	val id: String,
	val roadmapName: String,
	val addedAt: String
)

data class Roadmap(
	@SerializedName("id") val id: String,
	@SerializedName("roadmapName") val roadmapName: String,
	@SerializedName("addedAt") val addedAt: String
)

data class Course(
	@SerializedName("courseName") val courseName: String
)

data class CoursesResponse(
	@SerializedName("courses") val courses: List<Course>
)

data class SubCourseItem(
	val title: String,
	val description: String
)

data class SubCourseResponse(
	val message: String,
	val subcourses: List<SubCourse>
)

data class SubCourse(
	val subcourseName: String,
	val description: String
)

data class GenerateQuestionRequest(
	@SerializedName("text") val text: String
)

data class GenerateQuestionResponse(
	@SerializedName("generated_question") val generatedQuestions: List<GeneratedQuestion>
)

data class GeneratedQuestion(
	@SerializedName("Answer") val answer: String,
	@SerializedName("Distractor") val distractor: List<String>,
	@SerializedName("Question") val question: String
)

data class PointsRequest(
	@SerializedName("Points") val points: String,
	@SerializedName("courseName") val courseName: String
)

data class PointsResponse(
	@SerializedName("message") val message: String,
	@SerializedName("updatedUserPoint") val updatedUserPoint: Int
)


