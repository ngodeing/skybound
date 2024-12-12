package com.skybound.ui.quiz

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.skybound.data.remote.response.GeneratedQuestion
import com.skybound.data.remote.response.PointsResponse
import com.skybound.data.user.User
import com.skybound.data.user.UserRepository
import kotlinx.coroutines.launch

class QuizViewModel(private val repository: UserRepository) : ViewModel() {

    private val _questions = MutableLiveData<List<GeneratedQuestion>>()
    val questions: LiveData<List<GeneratedQuestion>> = _questions

    fun generateQuestions(text: String) {
        viewModelScope.launch {
            try {
                // Dummy data mimicking the API response
                val dummyQuestions = listOf(
                    GeneratedQuestion(
                        question = "Apa bahasa pemrograman yang paling populer saat ini?",
                        answer = "JavaScript",
                        distractor = listOf("Python", "Java", "C#")
                    ),
                    GeneratedQuestion(
                        question = "Apa yang dimaksud dengan framework dalam pengembangan perangkat lunak?",
                        answer = "Kerangka kerja untuk membangun aplikasi",
                        distractor = listOf("Library", "Database", "Server")
                    ),
                    GeneratedQuestion(
                        question = "Apa itu API dalam pengembangan perangkat lunak?",
                        answer = "Antarmuka Pemrograman Aplikasi",
                        distractor = listOf("Aplikasi", "Pengguna", "Internet")
                    ),
                    GeneratedQuestion(
                        question = "Apa itu metodologi Agile dalam pengembangan perangkat lunak?",
                        answer = "Pendekatan yang berfokus pada kolaborasi dan iterasi cepat",
                        distractor = listOf("Pengembangan berorientasi objek", "Pengujian perangkat lunak", "Manajemen proyek")
                    ),
                    GeneratedQuestion(
                        question = "Apa yang dimaksud dengan Continuous Integration (CI)?",
                        answer = "Proses menggabungkan kode dari banyak pengembang secara rutin",
                        distractor = listOf("Automasi testing", "Version control", "Debugging")
                    ),
                    GeneratedQuestion(
                        question = "Apa yang dimaksud dengan debugging dalam pemrograman?",
                        answer = "Proses mencari dan memperbaiki bug dalam kode",
                        distractor = listOf("Pembuatan dokumentasi", "Desain sistem", "Pengujian unit")
                    ),
                    GeneratedQuestion(
                        question = "Apa yang dimaksud dengan Full Stack Developer?",
                        answer = "Pengembang yang dapat bekerja pada bagian frontend dan backend aplikasi",
                        distractor = listOf("Frontend Developer", "Backend Developer", "DevOps Engineer")
                    )
                )
                // Simulate the response
                _questions.value = dummyQuestions
            } catch (e: Exception) {
                Log.e("QuizViewModel", "Error generating questions: ${e.message}")
            }
        }
    }

    fun submitPoints(points: Int, courseName: String, token: String, onResponse: (PointsResponse) -> Unit,onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.submitPoints(points, courseName, token)
                onResponse(response)
            } catch (e: Exception) {
                Log.e("QuizViewModel", "Error submitting points: ${e.message}")
                onError(e.message ?: "Terjadi kesalahan saat mengirim poin.")
            }
        }
    }

    fun getSession(): LiveData<User> {
        return repository.getSession().asLiveData()
    }
}

