package com.skybound.ui.quiz

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.skybound.MainActivity
import com.skybound.data.Question
import com.skybound.databinding.ActivityQuizBinding
import com.skybound.di.Injection
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

        courseName = intent.getStringExtra("courseTitle")

        val description = intent.getStringExtra("description") ?: ""

        quizViewModel.questions.observe(this) { generatedQuestions ->
            val adapter = QuizAdapter(
                generatedQuestions.map {
                    Question(it.question, it.answer, it.distractor)
                },
                onAnswerSelected = { position, isCorrect ->
                    if (isCorrect) totalPoints += 20
                },
                onNextClicked = { position ->
                    if (position == generatedQuestions.size - 1) {
                        showConfirmationDialog {
                            submitPoints(totalPoints)
                        }
                    } else {
                        binding.viewPager.currentItem = position + 1
                    }
                },
                onBackClicked = { position ->
                    if (position > 0) {
                        binding.viewPager.currentItem = position - 1
                    }
                }
            )
            binding.viewPager.adapter = adapter
        }
        quizViewModel.generateQuestions(description)
    }
    private fun showConfirmationDialog(onConfirm: () -> Unit) {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi")
            .setMessage("Apakah Anda yakin ingin menyelesaikan soal?")
            .setPositiveButton("Ya") { _, _ ->
                onConfirm()
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun submitPoints(points: Int) {
        courseName?.let { course ->
            quizViewModel.getSession().observe(this) { user ->
                val token = user.token
                quizViewModel.submitPoints(points, course, token,
                    onResponse = { response ->
                        Toast.makeText(
                            this,
                            "Selamat poin anda mendapatkan ${totalPoints} poin",
                            Toast.LENGTH_LONG
                        ).show()

                        val intent = Intent(this, MainActivity::class.java).apply {
                            putExtra("updatedPoints", response.updatedUserPoint)
                        }
                        startActivity(intent)
                        finish()
                    },
                    onError = { errorMessage ->
                        Toast.makeText(this, "Gagal mengirim poin: $errorMessage", Toast.LENGTH_LONG).show()
                    }
                )
            }
        }
    }

}



