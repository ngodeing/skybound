package com.skybound.ui.questionnaire

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.skybound.R
import com.skybound.data.questionnaire.Option
import com.skybound.data.questionnaire.Questionnaire
import com.skybound.databinding.ActivityQuestionnaireBinding

class QuestionnaireActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionnaireBinding
    private lateinit var adapter: QuestionnaireAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityQuestionnaireBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val questions = listOf(
                Questionnaire(
                    "Pekerjaan seperti apa yang Anda inginkan?",
                    listOf(
                        Option("Stabil", null, 1),
                        Option("Fleksibel", null, 2),
                        Option("Kolaborasi", null, 3),
                        Option("Berpergian", null, 4)
                    )
                ),
        Questionnaire(
            "Gaji yang Anda harapkan?",
            listOf(
                Option("Tinggi", null, 5),
                Option("Cukup", null, 3),
                Option("Rendah", null, 1)
            )
        ),
        Questionnaire(
            "Apa jenis pekerjaan yang paling Anda sukai?",
            listOf(
                Option("a", R.mipmap.cobacoba_foreground.toString(), 1),
                Option("b", R.mipmap.cobacoba_foreground.toString(), 2),
                Option("c", R.mipmap.cobacoba_foreground.toString(), 3)
            ),
            isImageQuestion = true,
            questionImageUrl = R.mipmap.cobacoba_foreground.toString()
        )
        )

        adapter = QuestionnaireAdapter(questions) { question, selectedAnswer, weight ->
            // Tangani pemilihan jawaban
            Toast.makeText(this, "You selected: $selectedAnswer for question: ${question.questionText}", Toast.LENGTH_SHORT).show()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.questionnaire)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}