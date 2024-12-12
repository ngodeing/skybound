package com.skybound.ui.quiz

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.skybound.data.Question
import com.skybound.databinding.ItemQuizBinding

class QuizAdapter(
    private val questions: List<Question>,
    private val onAnswerSelected: (position: Int, isCorrect: Boolean) -> Unit
) : RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    inner class QuizViewHolder(private val binding: ItemQuizBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(question: Question, position: Int) {
            binding.questionText.text = question.question

            val answer = question.distractor.toMutableList().apply {
                add(question.answer)
                shuffle()
            }

            binding.answerGroup.removeAllViews()
            answer.forEach { option ->
                val radioButton = RadioButton(binding.root.context).apply {
                    text = option
                }
                binding.answerGroup.addView(radioButton)
            }

            binding.answerGroup.setOnCheckedChangeListener { _, checkedId ->
                val selectedOption = binding.root.findViewById<RadioButton>(checkedId)?.text.toString()
                val isCorrect = selectedOption == question.answer
                onAnswerSelected(position, isCorrect)
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
