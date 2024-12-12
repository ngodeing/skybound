package com.skybound.ui.quiz

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skybound.data.remote.response.GeneratedQuestion
import com.skybound.data.user.UserRepository
import kotlinx.coroutines.launch

class QuizViewModel(private val repository: UserRepository) : ViewModel() {

    private val _questions = MutableLiveData<List<GeneratedQuestion>>()
    val questions: LiveData<List<GeneratedQuestion>> = _questions

    fun generateQuestions(text: String) {
        viewModelScope.launch {
            try {
                val generatedQuestions = repository.generateQuestions(text)
                _questions.value = generatedQuestions
            } catch (e: Exception) {
                Log.e("QuizViewModel", "Error generating questions: ${e.message}")
            }
        }
    }

}
