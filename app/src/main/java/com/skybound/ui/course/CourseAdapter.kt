package com.skybound.ui.course

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skybound.databinding.ItemCourseBinding
import com.skybound.data.remote.response.SubCourseItem

class CourseAdapter(
    private val items: List<SubCourseItem>,
    private val onItemClick: (SubCourseItem) -> Unit
) : RecyclerView.Adapter<CourseAdapter.SubCourseViewHolder>() {

    inner class SubCourseViewHolder(private val binding: ItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SubCourseItem) {
            binding.courseTitle.text = item.title
            binding.courseDescription.text = item.description
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCourseViewHolder {
        val binding = ItemCourseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SubCourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubCourseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
