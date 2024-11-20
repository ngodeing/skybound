package com.skybound.ui.roadmap2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.skybound.R
import com.skybound.data.RoadmapItem
import com.skybound.databinding.ActivityRoadMap2Binding
import com.skybound.ui.roadmap.Roadmap2Adapter

class RoadMap2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityRoadMap2Binding
    private lateinit var adapter: Roadmap2Adapter
    private lateinit var roadMap2ViewModel: RoadMap2ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRoadMap2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = Roadmap2Adapter(getRoadmapItems()) {
            item -> Toast.makeText(this, "Clicked: ${item.title}", Toast.LENGTH_SHORT).show()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.questionnaire)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getRoadmapItems(): List<RoadmapItem> {
        return listOf(
            RoadmapItem("Softare Engineer", "24/10/2024", "0"),
            RoadmapItem("Biologist", "24/10/2024", "0"),
            RoadmapItem("Architect", "24/10/2024", "0"),
            RoadmapItem("Doctorr", "24/10/2024", "0"),
            RoadmapItem("Attorney", "24/10/2024", "0"),
            RoadmapItem("Pengenalan DevOps", "24/10/2024", "0"),
            RoadmapItem("CI / CD", "24/10/2024", "0")
        )
    }
}