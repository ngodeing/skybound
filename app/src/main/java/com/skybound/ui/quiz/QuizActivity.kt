package com.skybound.ui.quiz

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.skybound.data.Question
import com.skybound.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val questions = listOf(
            Question(
                "Siapa saja di dunia, nasik kunsia bisa dimulai satu atau satu?",
                "Seseorang adalah tahu kuning",
                listOf("Penjara tujuan", "Bumi", "Makoskop")
            ),
            Question(
                "Komputernas moderne bisa repliksi dari rambut yang berbeda dan bahkan dari rambut berbeda?",
                "Berbeda berbeda",
                listOf("Mejamahkan komputer_devi", "Ikan", "Ikan")
            ),
            Question(
                "Siapa saja di dunia, nasik kunsia bisa dimulai satu atau satu?",
                "Seseorang adalah tahu kuning",
                listOf("Penjara tujuan", "Bumi", "Makoskop")
            ),
            Question(
                "Siapa saja di dunia, nasik kunsia bisa dimulai satu atau satu?",
                "Seseorang adalah tahu kuning",
                listOf("Penjara tujuan", "Bumi", "Makoskop")
            ),
            Question(
                "Siapa saja di dunia, nasik kunsia bisa dimulai satu atau satu?",
                "Seseorang adalah tahu kuning",
                listOf("Penjara tujuan", "Bumi", "Makoskop")
            ),

        )

        val adapter = QuizAdapter(questions) { position ->
            if (position < questions.size - 1) {
                binding.viewPager.currentItem = position + 1
            } else {
                Toast.makeText(this, "Quiz Selesai!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.viewPager.adapter = adapter
    }
}
