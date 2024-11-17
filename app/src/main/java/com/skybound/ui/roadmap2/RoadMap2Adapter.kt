package com.skybound.ui.roadmap

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skybound.R
import com.skybound.data.RoadmapItem
import com.skybound.databinding.ItemRoadmap2Binding

class Roadmap2Adapter(
    private val items: List<RoadmapItem>,
    private val onItemClick: (RoadmapItem) -> Unit
) : RecyclerView.Adapter<Roadmap2Adapter.Roadmap2ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Roadmap2ViewHolder {
        val binding = ItemRoadmap2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Roadmap2ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: Roadmap2ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class Roadmap2ViewHolder(private val binding: ItemRoadmap2Binding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RoadmapItem) {
            binding.itemRoadmap2.text = item.title
            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun getItemCount(): Int = items.size
}