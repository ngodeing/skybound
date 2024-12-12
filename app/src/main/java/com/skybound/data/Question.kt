package com.skybound.data

data class Question(
    val question: String,
    val answer: String,
    val distractor: List<String>
)