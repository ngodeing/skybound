package com.skybound.ui.questionnaire

import androidx.lifecycle.ViewModel

class QuestionnaireViewModel : ViewModel() {
    val answers = mutableMapOf<Int, String>()  // To store answers

    // Save answer for a specific question
    fun saveAnswer(questionIndex: Int, answer: String) {
        answers[questionIndex] = answer
    }

    val questions = QuestionnaireList.questions
}
