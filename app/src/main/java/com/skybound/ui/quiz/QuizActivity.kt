package com.skybound.ui.quiz

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.skybound.data.Question
import com.skybound.databinding.ActivityQuizBinding
import com.skybound.di.Injection
import com.skybound.ui.course.CourseAdapter
import com.skybound.ui.utils.ViewModelFactory

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private lateinit var quizViewModel: QuizViewModel
    private var totalPoints: Int = 0
    private var courseName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory(Injection.provideRepository(this))
        quizViewModel = ViewModelProvider(this, factory)[QuizViewModel::class.java]

        // Get courseName from intent
        courseName = intent.getStringExtra("courseTitle")

        val description = intent.getStringExtra("description") ?: ""

        // Observe generated questions
        quizViewModel.questions.observe(this) { generatedQuestions ->
            val adapter = QuizAdapter(generatedQuestions.map {
                Question(it.question, it.answer, it.distractor)
            }) { position, isCorrect ->
                if (isCorrect) totalPoints += 20

                if (position < generatedQuestions.size - 1) {
                    binding.viewPager.currentItem = position + 1
                } else {
                    submitPoints(totalPoints)
                }
            }
            binding.viewPager.adapter = adapter
        }
        quizViewModel.generateQuestions(description)
    }

    private fun submitPoints(points: Int) {
        courseName?.let { course ->
            quizViewModel.getSession().observe(this) { user ->
                val token = user.token
                quizViewModel.submitPoints(points, course, token) { response ->
                    Toast.makeText(
                        this,
                        "Selamat poin anda mendapatkan ${response.updatedUserPoint}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
    }
}



