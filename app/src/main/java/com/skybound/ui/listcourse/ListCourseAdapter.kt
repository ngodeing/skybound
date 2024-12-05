package com.skybound.ui.listcourse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skybound.data.CourseItem
import com.skybound.databinding.ItemListCourseBinding

class ListCourseAdapter(
    private val items: List<CourseItem>,
    private val onItemClick: (CourseItem) -> Unit
) : RecyclerView.Adapter<ListCourseAdapter.ListCourseViewHolder>() {

    inner class ListCourseViewHolder(private val binding: ItemListCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CourseItem) {
            binding.courseTitle.text = item.title
            binding.courseDateScore.text = item.date

            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCourseViewHolder {
        val binding = ItemListCourseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListCourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListCourseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
