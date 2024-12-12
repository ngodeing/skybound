package com.skybound.data

data class Question(
    val Question: String,
    val Answer: String,
    val Distractor: List<String>
)