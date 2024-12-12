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
                        question = "Komunisme sering disalahartikan dengan apa?",
                        answer = "komunisme internasional",
                        distractor = listOf("komunisme")
                    ),
                    GeneratedQuestion(
                        question = "Apa ideologi dasar yang digunakan oleh partai komunis di seluruh dunia?",
                        answer = "komunisme",
                        distractor = listOf("tanah", "partai politik", "populasi")
                    ),
                    GeneratedQuestion(
                        question = "Apa yang datang dari ide-ide Lenin?",
                        answer = "Marxisme-leninism",
                        distractor = listOf("pikiran", "Vladimir_Ilyich_Lenin", "Nikolai_ Lenin")
                    ),
                    GeneratedQuestion(
                        question = "Perubahan sosial dimulai dengan tenaga kerja atau, lebih umum lagi, dengan proletariat, tapi organisasi tenaga kerja itu hanya bisa berhasil melalui apa?",
                        answer = "perjuangan",
                        distractor = listOf("pergeseran", "organisasi", "proletariat")
                    ),
                    GeneratedQuestion(
                        question = "Apa yang dibutuhkan partai untuk menjadi think tank?,",
                        answer = "pesta",
                        distractor = listOf("merenungkan", "perusahaan", "cerebrate")
                    ),
                    GeneratedQuestion(
                        question = "Apa yang dapat ditanamkan jika terjadi karena poliburo?",
                        answer = "perubahan sosial",
                        distractor = emptyList()
                    ),
                    GeneratedQuestion(
                        question = "Apa yang memperkenalkan penggunaan sistem perwakilan demokratis oleh elit partai komunis, karena itu sangat membatasi demokrasi secara langsung pada orang-orang yang bukan anggota partai komunis, karena dalam pengertian komunisme, tidak ada hak-hak pribadi yang diketahui, seperti dalam pengertian liberalisme?",
                        answer = "komunisme",
                        distractor = listOf("sensory_faculty", "kemakmuran", "skema")
                    )
                )
                // Simulate the response
                _questions.value = dummyQuestions
            } catch (e: Exception) {
                Log.e("QuizViewModel", "Error generating questions: ${e.message}")
            }
        }
    }

    fun submitPoints(points: Int, courseName: String, token: String, onResponse: (PointsResponse) -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.submitPoints(points, courseName, token)
                onResponse(response)
            } catch (e: Exception) {
                Log.e("QuizViewModel", "Error submitting points: ${e.message}")
            }
        }
    }

    fun getSession(): LiveData<User> {
        return repository.getSession().asLiveData()
    }
}

