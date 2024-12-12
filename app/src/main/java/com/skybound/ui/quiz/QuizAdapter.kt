package com.skybound.ui.quiz

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.skybound.data.Question
import com.skybound.databinding.ItemQuizBinding

class QuizAdapter(
    private val questions: List<Question>,
    private val onNextClicked: (Int) -> Unit
) : RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    class QuizViewHolder(val binding: ItemQuizBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val binding = ItemQuizBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuizViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val question = questions[position]

        holder.binding.questionText.text = question.Question

        val options = listOf(question.Answer) + question.Distractor
        val shuffledOptions = options.shuffled()

        shuffledOptions.forEachIndexed { index, option ->
            (holder.binding.answerGroup.getChildAt(index) as? RadioButton)?.text = option
        }

        holder.binding.nextButton.setOnClickListener {
            onNextClicked(position)
        }
    }

    override fun getItemCount() = questions.size
}
