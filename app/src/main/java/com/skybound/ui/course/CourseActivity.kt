package com.skybound.ui.course

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.skybound.databinding.ActivityCourseBinding
import com.skybound.data.SubCourseItem

class CourseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCourseBinding
    private lateinit var courseAdapter: CourseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val courseTitle = intent.getStringExtra("courseTitle")
        if (courseTitle != null) {
            binding.title.text = courseTitle
        }

        courseAdapter = CourseAdapter(getSubCourseItems())

        binding.courseBodyRecyclerView.apply {
            adapter = courseAdapter
            layoutManager = LinearLayoutManager(this@CourseActivity)
        }
    }

    private fun getSubCourseItems(): List<SubCourseItem> {
        return listOf(
            SubCourseItem(
                "Pengertian Software",
                "Software adalah sekumpulan instruksi atau program yang dirancang untuk menjalankan tugas-tugas tertentu pada perangkat komputer. Berbeda dengan perangkat keras (hardware) yang merupakan komponen fisik, software bersifat tidak berwujud dan berfungsi sebagai penghubung antara pengguna dan perangkat keras untuk menjalankan operasi. Software terdiri dari beberapa jenis, seperti sistem operasi yang mengatur sumber daya komputer, aplikasi yang digunakan untuk menyelesaikan pekerjaan spesifik, serta perangkat lunak pendukung lainnya seperti driver atau utilitas. Dengan software, komputer dapat digunakan untuk berbagai keperluan, mulai dari pengolahan data hingga hiburan, sesuai dengan fungsionalitas yang dirancang oleh pengembangnya."
            ),
            SubCourseItem(
                "Bagaimana Software bekerja",
                "Software bekerja dengan memberikan serangkaian instruksi kepada perangkat keras (hardware) komputer untuk menjalankan tugas tertentu. Ketika dijalankan, software berinteraksi dengan sistem operasi untuk mengakses sumber daya seperti memori, prosesor, dan perangkat input/output. Instruksi-instruksi dalam software, yang awalnya ditulis dalam bahasa pemrograman, diterjemahkan ke dalam bahasa mesin agar dimengerti oleh CPU. Prosesor kemudian membaca dan mengeksekusi instruksi tersebut, menghasilkan output sesuai fungsi software, seperti pengolahan data, pengendalian perangkat, atau menjalankan aplikasi tertentu. Dengan cara ini, software memungkinkan perangkat keras bekerja sesuai kebutuhan pengguna."
            )
        )
    }
}
