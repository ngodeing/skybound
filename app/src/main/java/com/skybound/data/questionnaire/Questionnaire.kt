package com.skybound.data.questionnaire

import java.io.Serializable

data class Questionnaire(
    val id: Int,
    val questionText: String,
    val options: List<Option>,
    val isImageQuestion: Boolean = false,
    val questionImageUrl: String? = null
) : Serializable

data class Option(
    val answerText: String,
    val answerImageUrl: String? = null,
    val weight: Float
) : Serializable