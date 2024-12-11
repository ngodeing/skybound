package com.skybound.ui.course

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.skybound.databinding.ActivityCourseBinding
import com.skybound.di.Injection
import com.skybound.ui.profile.ProfileViewModel
import com.skybound.ui.utils.ViewModelFactory

class CourseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCourseBinding
    private lateinit var courseAdapter: CourseAdapter
    private lateinit var courseViewModel: CourseViewModel
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val courseTitle = intent.getStringExtra("courseTitle")
        val roadmapName = intent.getStringExtra("roadmapName")
        binding.title.text = courseTitle ?: "Course"

        val factory = ViewModelFactory(Injection.provideRepository(this))
        courseViewModel = ViewModelProvider(this, factory)[CourseViewModel::class.java]
        profileViewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]


        fun observeUser(token: String, courseTitle: String) {
            profileViewModel.fetchUser(token)
            profileViewModel.user.observe(this) { user ->
                val roadmap = user.roadmaps
                courseViewModel.fetchSubCourses(token, roadmap, courseTitle)
            }
        }

        courseViewModel.getSession().observe(this) { user ->
            val token = user.token
            courseTitle?.let {
                observeUser(token, courseTitle)
            }
        }

        courseAdapter = CourseAdapter(emptyList())

        binding.courseBodyRecyclerView.apply {
            adapter = courseAdapter
            layoutManager = LinearLayoutManager(this@CourseActivity)
        }

        val token = "your_bearer_token_here"
        if (roadmapName != null && courseTitle != null) {
            courseViewModel.fetchSubCourses(token, roadmapName, courseTitle)
        }

        courseViewModel.subCourses.observe(this) { subCourses ->
            courseAdapter = CourseAdapter(subCourses)
            binding.courseBodyRecyclerView.adapter = courseAdapter
        }
    }
}
