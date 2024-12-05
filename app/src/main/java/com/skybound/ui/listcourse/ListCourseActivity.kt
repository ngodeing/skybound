package com.skybound.ui.listcourse

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.skybound.databinding.ActivityListCourseBinding
import com.skybound.data.CourseItem
import com.skybound.ui.course.CourseActivity

class ListCourseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListCourseBinding
    private lateinit var courseAdapter: ListCourseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val courseTitle = intent.getStringExtra("courseTitle")
        if (courseTitle != null) {
            binding.title.text = courseTitle
        }
        courseAdapter = ListCourseAdapter(getCourseItems()) { item ->
            val intent = Intent(this, CourseActivity::class.java).apply {
                putExtra("courseTitle", item.title)
            }
            startActivity(intent)
        }
        binding.courseRecyclerView.apply {
            adapter = courseAdapter
            layoutManager = LinearLayoutManager(this@ListCourseActivity)
        }
    }

    private fun getCourseItems(): List<CourseItem> {
        return listOf(
            CourseItem("Pengenalan Software", "24/10/2024 - 31/10/2024"),
            CourseItem("Apa itu software engineer", "24/10/2024 - 31/10/2024"),
            CourseItem("Pengenalan Software", "24/10/2024 - 31/10/2024"),
            CourseItem("Apa itu software engineer", "24/10/2024 - 31/10/2024"),
            CourseItem("Pengenalan Software", "24/10/2024 - 31/10/2024"),
            CourseItem("Apa itu software engineer", "24/10/2024 - 31/10/2024"),
            CourseItem("Pengenalan Software", "24/10/2024 - 31/10/2024"),
            CourseItem("Apa itu software engineer", "24/10/2024 - 31/10/2024")
        )
    }
}
