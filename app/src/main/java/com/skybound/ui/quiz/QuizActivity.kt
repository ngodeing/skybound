package com.skybound.ui.quiz

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.skybound.data.Question
import com.skybound.databinding.ActivityQuizBinding
import com.skybound.di.Injection
import com.skybound.ui.utils.ViewModelFactory

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private lateinit var quizViewModel: QuizViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory(Injection.provideRepository(this))
        quizViewModel = ViewModelProvider(this, factory)[QuizViewModel::class.java]

        val description = intent.getStringExtra("description") ?: ""

        binding.progressBar.visibility = View.VISIBLE

        quizViewModel.questions.observe(this) { generatedQuestions ->
            binding.progressBar.visibility = View.GONE

            val adapter = QuizAdapter(generatedQuestions.map {
                Question(it.question, it.answer, it.distractor)
            }) { position ->
                if (position < generatedQuestions.size - 1) {
                    binding.viewPager.currentItem = position + 1
                } else {
                    Toast.makeText(this, "Quiz Selesai!", Toast.LENGTH_SHORT).show()
                }
            }
            binding.viewPager.adapter = adapter
        }

        quizViewModel.generateQuestions(description)
    }
}


