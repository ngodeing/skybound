package com.skybound.ui.roadmap2

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.button.MaterialButton
import com.skybound.MainActivity
import com.skybound.R
import com.skybound.databinding.ActivityRoadMap2Binding

class RoadMap2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityRoadMap2Binding
    private var selectedButton: MaterialButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRoadMap2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val predictedCareers = intent.getStringArrayListExtra("predictedCareers") ?: emptyList()
        val builder = AlertDialog.Builder(this)

        binding.optionsFlexboxLayout.removeAllViews()

        val flexboxLayout = binding.optionsFlexboxLayout
        flexboxLayout.flexDirection = FlexDirection.COLUMN
        predictedCareers.forEach { career ->
            val button = MaterialButton(this).apply {
                text = career
                setBackgroundColor(resources.getColor(R.color.buttonColor)) // Set warna default
                setTextColor(resources.getColor(android.R.color.white))
                gravity = Gravity.CENTER
                isAllCaps = false
                layoutParams = FlexboxLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    marginEnd = 8
                    marginStart = 8
                }

                // Menambahkan logika ketika button ditekan
                setOnClickListener {
                    // Reset background color button yang sebelumnya dipilih
                    selectedButton?.setBackgroundColor(resources.getColor(R.color.buttonColor))

                    // Ubah warna button yang dipilih
                    setBackgroundColor(resources.getColor(R.color.primaryColor))

                    // Simpan button yang dipilih
                    selectedButton = this

                    builder.setTitle("Konfirmasi")
                    builder.setMessage("Apakah Anda yakin dengan pilihan Anda?\n\nSetelah ini anda tidak bisa mengubah roadmap pilihan anda")
                    builder.setPositiveButton("Yakin") { dialog, _ ->
                        // Proses penghitungan dan persiapan data untuk POST API
                        val intent = Intent(this@RoadMap2Activity, MainActivity::class.java)  // Ganti dengan Activity yang sesuai
                        intent.putExtra("selectedCareer", career)
                        startActivity(intent)
                        dialog.dismiss()
                    }
                    builder.setNegativeButton("Tidak") { dialog, _ ->
                        // Membiarkan pengguna tetap melanjutkan menjawab
                        dialog.dismiss()
                    }
                    builder.show()
                }
            }
            binding.optionsFlexboxLayout.addView(button)
        }

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.questionnaire)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

//    private fun getRoadmapItems(): List<RoadmapItem> {
//        return listOf(
//            RoadmapItem("Softare Engineer", "24/10/2024", "0"),
//            RoadmapItem("Biologist", "24/10/2024", "0"),
//            RoadmapItem("Architect", "24/10/2024", "0"),
//            RoadmapItem("Doctorr", "24/10/2024", "0"),
//            RoadmapItem("Attorney", "24/10/2024", "0"),
//            RoadmapItem("Pengenalan DevOps", "24/10/2024", "0"),
//            RoadmapItem("CI / CD", "24/10/2024", "0")
//        )
//    }
}