package com.skybound.data.questionnaire

data class Questionnaire(
    val questionText: String,
    val options: List<Option>,
    val isImageQuestion: Boolean = false,
    val questionImageUrl: String? = null
)

data class Option(
    val answerText: String? = null,
    val answerImageUrl: String? = null,
    val weight: Int
)