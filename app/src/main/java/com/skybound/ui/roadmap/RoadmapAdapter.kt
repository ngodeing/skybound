package com.skybound.ui.roadmap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skybound.R
import com.skybound.data.RoadmapItem

class RoadmapAdapter(
    private val items: List<RoadmapItem>,
    private val onItemClick: (RoadmapItem) -> Unit
) : RecyclerView.Adapter<RoadmapAdapter.RoadmapViewHolder>() {

    inner class RoadmapViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.course_title)
        private val dateScoreTextView: TextView = itemView.findViewById(R.id.course_date_score)

        fun bind(item: RoadmapItem) {
            titleTextView.text = item.title
            dateScoreTextView.text = "${item.date}\nSkor : ${item.score}"
            itemView.setOnClickListener { onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoadmapViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_roadmap, parent, false)
        return RoadmapViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoadmapViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}