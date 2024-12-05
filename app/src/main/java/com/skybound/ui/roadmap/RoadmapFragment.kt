package com.skybound.ui.roadmap

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skybound.R
import com.skybound.data.RoadmapItem
import com.skybound.ui.listcourse.ListCourseActivity

class RoadmapFragment : Fragment() {

    private lateinit var roadmapRecyclerView: RecyclerView
    private lateinit var roadmapAdapter: RoadmapAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_roadmap, container, false)
        roadmapRecyclerView = view.findViewById(R.id.roadmap_recycler_view)
        roadmapAdapter = RoadmapAdapter(getRoadmapItems()) { item ->
            val intent = Intent(requireContext(), ListCourseActivity::class.java)
            intent.putExtra("courseTitle", item.title)
            startActivity(intent)
        }

        roadmapRecyclerView.adapter = roadmapAdapter
        roadmapRecyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    private fun getRoadmapItems(): List<RoadmapItem> {
        return listOf(
            RoadmapItem("Introduction to software", "24/10/2024", "0"),
            RoadmapItem("Project Management", "24/10/2024", "0"),
            RoadmapItem("Git dan Github", "24/10/2024", "0"),
            RoadmapItem("Algoritma Pemrograman", "24/10/2024", "0"),
            RoadmapItem("Fundamental Computer Science", "24/10/2024", "0"),
            RoadmapItem("Pengenalan DevOps", "24/10/2024", "0"),
            RoadmapItem("CI / CD", "24/10/2024", "0")
        )
    }
}
