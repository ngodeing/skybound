package com.skybound.ui.questionnaire

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.button.MaterialButton
import com.skybound.R
import com.skybound.data.questionnaire.Questionnaire
import com.skybound.data.questionnaire.Option

class QuestionnaireAdapter(
    private val questions: List<Questionnaire>,
    private val onAnswerSelected: (Questionnaire, String, Int) -> Unit // Callback to handle the selected answer and weight
) : RecyclerView.Adapter<QuestionnaireAdapter.QuestionViewHolder>() {

    // Track selected answers and their weights per question
    private val selectedAnswers = mutableMapOf<Int, Option>()

    inner class QuestionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val questionText: TextView = view.findViewById(R.id.questionText)
        val questionImage: ImageView = view.findViewById(R.id.questionImage)
        val buttonGroup: FlexboxLayout = view.findViewById(R.id.buttonGroup)

        fun bind(question: Questionnaire) {
            // Check if the question is image-based
            if (question.isImageQuestion) {
                questionText.visibility = View.VISIBLE
                questionImage.visibility = View.VISIBLE
                questionText.text = question.questionText

                // Use setImageResource for local resources like drawable or mipmap
                question.questionImageUrl?.toInt()?.let {
                    questionImage.setImageResource(it) // Set image from drawable or mipmap
                } ?: run {
                    // If no image resource is provided, you can set a default image
                    questionImage.setImageResource(R.mipmap.cobacoba_foreground)
                }
            } else {
                questionText.visibility = View.VISIBLE
                questionImage.visibility = View.GONE
                questionText.text = question.questionText
            }

            buttonGroup.removeAllViews()

            question.options.forEach { option ->
                val button = MaterialButton(buttonGroup.context).apply {
                    text = option.answerText
                    setBackgroundColor(ContextCompat.getColor(context, R.color.buttonColor))

                    layoutParams = FlexboxLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).apply {
                        marginEnd = 10
                    }

                    // Menambahkan gambar pada tombol jika ada
                    if (option.answerImageUrl != null) {
                        val drawable = ContextCompat.getDrawable(context, option.answerImageUrl.toInt())
                        drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                        setCompoundDrawables(null, null, drawable, null)
                    }

                    setOnClickListener {
                        onAnswerSelected(question, option.answerText ?: "", option.weight)
                        selectedAnswers[adapterPosition] = option
                        updateButtonColor(this, option)
                        updateOtherButtonsColor(question)
                    }
                }
                buttonGroup.addView(button)
            }
        }


        private fun updateButtonColor(button: MaterialButton, selectedOption: Option) {
            if (selectedAnswers[adapterPosition] == selectedOption) {
                button.setBackgroundColor(ContextCompat.getColor(button.context, R.color.primaryColor))
            } else {
                button.setBackgroundColor(ContextCompat.getColor(button.context, R.color.buttonColor))
            }
        }

        private fun updateOtherButtonsColor(question: Questionnaire) {
            val buttons = buttonGroup.children.toList()
            buttons.forEach { button ->
                val materialButton = button as MaterialButton
                val option = question.options.find { it.answerText == materialButton.text }
                option?.let {
                    if (selectedAnswers[adapterPosition] == it) {
                        materialButton.setBackgroundColor(ContextCompat.getColor(materialButton.context, R.color.primaryColor))
                    } else {
                        materialButton.setBackgroundColor(ContextCompat.getColor(materialButton.context, R.color.buttonColor))
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]
        holder.bind(question)
    }

    override fun getItemCount(): Int = questions.size
}


//    question.options.forEach { option ->
//        val button = MaterialButton(buttonGroup.context).apply {
//            if (option.answerText != null) {
//                text = option.answerText
//            } else if (option.answerImageUrl != null) {
//                // Set image for answer if the option contains an image
//                val imageView = ImageView(buttonGroup.context).apply {
//                    Glide.with(context)
//                        .load(option.answerImageUrl) // Load image URL for answer button
//                        .into(this)
//                }
//                buttonGroup.addView(imageView)
//                return@apply
//            }
//
//            setBackgroundColor(ContextCompat.getColor(context, R.color.buttonColor))
//            layoutParams = FlexboxLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            ).apply {
//                marginEnd = 10 // Add marginEnd of 2dp
//            }
//
//            setOnClickListener {
//                // Update selected answer and its weight
//                onAnswerSelected(question, option.answerText ?: "", option.weight)
//                selectedAnswers[adapterPosition] = option
//                updateButtonColor(this, option)
//                updateOtherButtonsColor(question)
//            }
//        }
//        buttonGroup.addView(button)
//    }
