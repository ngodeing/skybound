package com.skybound.ui.questionnaire

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.button.MaterialButton
import com.skybound.R
import com.skybound.databinding.ActivityQuestionnaireBinding
import com.skybound.helper.CareerPredictionHelper
import com.skybound.ui.roadmap2.RoadMap2Activity

@Suppress("DEPRECATION")
class QuestionnaireActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionnaireBinding
    private lateinit var viewModel: QuestionnaireViewModel
    private var currentIndex = 0
    private var selectedButton: MaterialButton? = null  // Untuk menyimpan button yang dipilih

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionnaireBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(QuestionnaireViewModel::class.java)

        // Set initial question
        loadQuestion()

//        binding.predictionResultText.visibility = View.VISIBLE
//        binding.predictionResultText.text = selectedCareer

        // Next button functionality
        binding.nextButton.setOnClickListener {
            if (isQuestionAnswered()) {  // Check if the current question has been answered
                if (currentIndex < viewModel.questions.size - 1) {
                    currentIndex++
                    loadQuestion()

                    // Jika sudah mencapai soal terakhir, ubah teks tombol menjadi "Submit"
                    if (currentIndex == viewModel.questions.size - 1) {
                        binding.nextButton.text = "Submit"
                    }
                } else {
                    showConfirmationDialog()
                }
            } else {
                // Show a message to remind the user to answer the question
                Toast.makeText(this, "Please select an answer before proceeding.", Toast.LENGTH_SHORT).show()
            }
        }


        // Back button functionality
        binding.backButton.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                loadQuestion()
                binding.nextButton.text = "Next"
            } else {
                Toast.makeText(this, "Ini adalah soal pertama.", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.questionnaire)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun loadQuestion() {
        val question = viewModel.questions[currentIndex]

        // Set question text
        binding.questionText.text = question.questionText
        binding.questionText.setTextColor(resources.getColor(R.color.titleColor))

        // Set image if available
        if (question.isImageQuestion) {
            val imageResId = resources.getIdentifier(question.questionImageUrl, "mipmap", packageName)
            if (imageResId != 0) {
                binding.questionImage.visibility = View.VISIBLE
                binding.questionImage.setImageResource(imageResId) // Set image directly from mipmap
            } else {
                binding.questionImage.visibility = View.GONE
            }
        } else {
            binding.questionImage.visibility = View.GONE
        }

        // Clear previous options in FlexboxLayout
        binding.optionsFlexboxLayout.removeAllViews()
        val flexboxLayout = binding.optionsFlexboxLayout
        flexboxLayout.flexDirection = FlexDirection.COLUMN
        // Add answer options dynamically
        question.options.forEach { option ->
            val button = MaterialButton(this).apply {
                text = option.answerText
                setBackgroundColor(resources.getColor(R.color.buttonColor))
                setTextColor(resources.getColor(android.R.color.white))

                gravity = Gravity.FILL

                isAllCaps = false

                layoutParams = FlexboxLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    marginEnd = 8
                }

                option.answerImageUrl?.let {
                    val imageResId = resources.getIdentifier(it, "mipmap", packageName)
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, imageResId, 0) // Set gambar di sisi kanan tombol
                    text = null
                }

                if (viewModel.answers[question.id] == option.answerText) {
                    setBackgroundColor(resources.getColor(R.color.primaryColor))
                    selectedButton = this // Track the selected button
                }

                // Handle option selection
                setOnClickListener {
                    // Reset background color of the previously selected button (if any)
                    selectedButton?.setBackgroundColor(resources.getColor(R.color.buttonColor))

                    // Change selected option color
                    setBackgroundColor(resources.getColor(R.color.primaryColor))

                    // Save the selected answer
                    viewModel.saveAnswer(question.id, option.answerText)

                    // Update the selected button reference
                    selectedButton = this
                    Log.d("SelectedWeight", "Option: ${option.answerText}, Weight: ${option.weight}")
                    updateNextButtonState()
                }
            }
            binding.optionsFlexboxLayout.addView(button)
        }
        updateNextButtonState()
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Konfirmasi")
        builder.setMessage("Apakah Anda yakin dengan semua jawaban Anda?\n\nSetelah ini anda tidak bisa mengubah/mengisi ulang jawaban anda")
        builder.setPositiveButton("Yakin") { dialog, _ ->
            // Proses penghitungan dan persiapan data untuk POST API
            calculateAndSubmitResults()
            dialog.dismiss()
        }
        builder.setNegativeButton("Tidak") { dialog, _ ->
            // Membiarkan pengguna tetap melanjutkan menjawab
            dialog.dismiss()
        }
        builder.show()
    }

    private fun isQuestionAnswered(): Boolean {
        // Check if the current question has been answered
        return viewModel.answers[viewModel.questions[currentIndex].id] != null
    }

    private fun updateNextButtonState() {
        // Enable/Disable Next button based on whether the current question has been answered
        if (isQuestionAnswered()) {
            binding.nextButton.isEnabled = true
            binding.nextButton.setBackgroundColor(resources.getColor(R.color.primaryColor))
        } else {
            binding.nextButton.isEnabled = false
            binding.nextButton.setBackgroundColor(resources.getColor(R.color.buttonColor)) // Disabled color
        }
    }

    private fun calculateAndSubmitResults() {
        // Menghitung rata-rata skor per kategori
        val categories = listOf(
            "Openness",
            "Conscientiousness",
            "Extraversion",
            "Agreeableness",
            "Neuroticism",
            "Numerical Aptitude",
            "Spatial Aptitude",
            "Perceptual Aptitude",
            "Abstract Reasoning",
            "Verbal Reasoning"
        )
        val categoryScores = mutableMapOf<String, Float>()
        val categoryCounts = mutableMapOf<String, Int>()

        // Kategorisasi dan perhitungan rata-rata
        viewModel.questions.forEach { question ->
            val category = when {
                question.id in 1..5 -> "Openness"
                question.id in 6..10 -> "Conscientiousness"
                question.id in 11..15 -> "Extraversion"
                question.id in 16..20 -> "Agreeableness"
                question.id in 21..25 -> "Neuroticism"
                question.id in 26..30 -> "Numerical Aptitude"
                question.id in 31..35 -> "Spatial Aptitude"
                question.id in 36..40 -> "Perceptual Aptitude"
                question.id in 41..45 -> "Abstract Reasoning"
                question.id in 46..50 -> "Verbal Reasoning"
                else -> ""
            }

            question.options.forEach { option ->
                if (viewModel.answers[question.id] == option.answerText) {
                    categoryScores[category] =
                        categoryScores.getOrDefault(category, 0.0f) + option.weight
                    categoryCounts[category] = categoryCounts.getOrDefault(category, 0) + 1
                }
            }
        }

        // Menghitung rata-rata untuk setiap kategori
        val categoryAverages = mutableMapOf<String, Float>()
        categories.forEach { category ->
            val totalScore = categoryScores[category] ?: 0.0f
            val totalCount = categoryCounts[category] ?: 0
            categoryAverages[category] = if (totalCount > 0) totalScore / totalCount else 0.0f
        }

        val inputFeatures = FloatArray(10).apply {
            categoryAverages.entries.forEachIndexed { index, entry ->
                this[index] =
                    entry.value // Menggunakan rata-rata kategori untuk fitur input

            }
        }
        Log.d("InputFeatures", inputFeatures.joinToString(", "))

        val careerPredictionHelper = CareerPredictionHelper(this, "recommender-v2.tflite")
        val predictedCareers = careerPredictionHelper.predictCareers(inputFeatures)

        val intent = Intent(this, RoadMap2Activity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putStringArrayListExtra("predictedCareers", ArrayList(predictedCareers))
        startActivity(intent)
        finish()
        // Menampilkan hasil prediksi karir
//        val predictionResultText =
//            "Top 3 Karir yang Direkomendasikan:\n" + predictedCareers.joinToString(", ")
//        binding.predictionResultText.visibility = View.VISIBLE
//        binding.predictionResultText.text = predictionResultText
    }
}
