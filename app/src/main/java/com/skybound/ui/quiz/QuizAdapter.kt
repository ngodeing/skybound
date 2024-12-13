package com.skybound.ui.quiz

import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.skybound.R
import com.skybound.data.Question
import com.skybound.databinding.ItemQuizBinding

class QuizAdapter(
    private val questions: List<Question>,
    private val onAnswerSelected: (position: Int, isCorrect: Boolean) -> Unit,
    private val onNextClicked: (position: Int) -> Unit,
    private val onBackClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    inner class QuizViewHolder(private val binding: ItemQuizBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(question: Question, position: Int) {
            binding.questionText.text = question.question

            val answers = question.distractor.toMutableList().apply {
                add(question.answer)
                shuffle()
            }

            binding.answerGroup.removeAllViews()

            var selectedButton: Button? = null

            answers.forEach { option ->
                val button = Button(binding.root.context).apply {
                    text = option
                    background = GradientDrawable().apply {
                        setColor(ContextCompat.getColor(binding.root.context, R.color.buttonColor))
                        cornerRadius = 16f
                    }
                    setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            android.R.color.white
                        )
                    )
                    setTypeface(typeface, Typeface.BOLD)
                    setOnClickListener {
                        selectedButton?.background = GradientDrawable().apply {
                            setColor(ContextCompat.getColor(binding.root.context, R.color.buttonColor))
                            cornerRadius = 16f
                        }

                        background = GradientDrawable().apply {
                            setColor(ContextCompat.getColor(binding.root.context, R.color.primaryColor))
                            cornerRadius = 16f
                        }
                        selectedButton = this
                        val isCorrect = option == question.answer
                        onAnswerSelected(position, isCorrect)
                    }
                }
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 8, 0, 8)
                }
                button.layoutParams = layoutParams
                binding.answerGroup.addView(button)
            }
            binding.nextButton.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    onNextClicked(position)
                }
            }
            binding.backButton.apply {
                visibility = if (position > 0) View.VISIBLE else View.GONE
                setOnClickListener {
                    onBackClicked(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val binding = ItemQuizBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuizViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.bind(questions[position], position)
    }

    override fun getItemCount(): Int = questions.size
}

