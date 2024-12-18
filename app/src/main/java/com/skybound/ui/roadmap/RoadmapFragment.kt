package com.skybound.ui.roadmap

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skybound.R
import com.skybound.di.Injection
import com.skybound.ui.course.CourseActivity
import com.skybound.ui.profile.ProfileViewModel
import com.skybound.ui.utils.ViewModelFactory

class RoadmapFragment : Fragment() {

    private lateinit var roadmapRecyclerView: RecyclerView
    private lateinit var roadmapAdapter: RoadmapAdapter
    private lateinit var roadmapViewModel: RoadmapViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var titleTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_roadmap, container, false)
        roadmapRecyclerView = view.findViewById(R.id.roadmap_recycler_view)
        titleTextView = view.findViewById(R.id.title)

        roadmapAdapter = RoadmapAdapter(emptyList()) { item ->
            val intent = Intent(requireContext(), CourseActivity::class.java)
            intent.putExtra("courseTitle", item.title)
            startActivity(intent)
        }
        roadmapRecyclerView.adapter = roadmapAdapter
        roadmapRecyclerView.layoutManager = LinearLayoutManager(context)

        val factory = ViewModelFactory(Injection.provideRepository(requireContext()))
        roadmapViewModel = ViewModelProvider(this, factory)[RoadmapViewModel::class.java]
        profileViewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]

        fun observeUser(token: String) {
            profileViewModel.fetchUser(token)
            profileViewModel.user.observe(viewLifecycleOwner) { user ->
                titleTextView.text = user.roadmaps
            }
        }

        roadmapViewModel.getSession().observe(viewLifecycleOwner) { user ->
            val token = user.token
            if (token.isNotEmpty()) {
                observeUser(token)
                roadmapViewModel.fetchCourses(token)
            }
        }

        roadmapViewModel.courses.observe(viewLifecycleOwner) { courses ->
            roadmapAdapter = RoadmapAdapter(courses) { item ->
                val intent = Intent(requireContext(), CourseActivity::class.java)
                intent.putExtra("courseTitle", item.title)
                startActivity(intent)
            }
            roadmapRecyclerView.adapter = roadmapAdapter
        }

        return view
    }
}
