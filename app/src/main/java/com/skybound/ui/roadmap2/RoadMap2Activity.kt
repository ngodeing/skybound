package com.skybound.ui.roadmap2

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.button.MaterialButton
import com.skybound.MainActivity
import com.skybound.R
import com.skybound.databinding.ActivityRoadMap2Binding
import com.skybound.ui.aftersignin.AfterSignInActivity
import com.skybound.ui.utils.ViewModelFactory
import kotlinx.coroutines.launch

class RoadMap2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityRoadMap2Binding
    private var selectedButton: MaterialButton? = null
    private lateinit var viewmodel: RoadMap2ViewModel
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRoadMap2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi ViewModel
        val factory = ViewModelFactory.getInstance(this)
        viewmodel = ViewModelProvider(this, factory)[RoadMap2ViewModel::class.java]

        // Mengambil data career yang diprediksi dari Intent
        val predictedCareers = intent.getStringArrayListExtra("predictedCareers") ?: emptyList()

        // Ambil token pengguna dari session
        getTokenFromSession()

        // Membuat dan menampilkan buttons berdasarkan predictedCareers
        setupCareerButtons(predictedCareers)

        // Menangani perubahan UI terkait window insets
        handleWindowInsets()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.questionnaire)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getTokenFromSession() {
        lifecycleScope.launch {
            // Menggunakan coroutine untuk memanggil collect()
            viewmodel.getSession().collect { user ->
                token = user.token  // Menyimpan token yang didapatkan dari session
            }
        }
    }

    private fun setupCareerButtons(predictedCareers: List<String>) {
        binding.optionsFlexboxLayout.removeAllViews()

        val flexboxLayout = binding.optionsFlexboxLayout
        flexboxLayout.flexDirection = FlexDirection.COLUMN

        // Membuat button untuk setiap karir yang diprediksi
        predictedCareers.forEach { career ->
            val button = createCareerButton(career)
            binding.optionsFlexboxLayout.addView(button)
        }
    }

    private fun createCareerButton(career: String): MaterialButton {
        return MaterialButton(this).apply {
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

                // Menampilkan dialog konfirmasi
                showConfirmationDialog(career)
            }
        }
    }

    private fun showConfirmationDialog(career: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Konfirmasi")
        builder.setMessage("Apakah Anda yakin dengan pilihan Anda?\n\nSetelah ini anda tidak bisa mengubah roadmap pilihan anda")
        builder.setPositiveButton("Yakin") { dialog, _ ->

            if (token != null) {
                val deadline = "20-12-2024"

                viewmodel.postRoadmap(token!!, career, deadline).observe(this) { result ->
                    when (result) {
                        is Result.Success -> {
                            val intent = Intent(this@RoadMap2Activity, MainActivity::class.java)
                            intent.putExtra("selectedCareer", career)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()
                        }

                        is Result.Error -> {
                            showRetryDialog()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Token tidak ditemukan, coba login ulang.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        builder.show()
    }

    private fun showRetryDialog() {
        val retryBuilder = AlertDialog.Builder(this)
        retryBuilder.setTitle("Gagal Menambahkan Roadmap")
        retryBuilder.setMessage("Penambahan roadmap gagal, apakah Anda ingin menunggu atau mengisi ulang?")
        retryBuilder.setPositiveButton("Isi Ulang") { dialog, _ ->
            val intent = Intent(this, AfterSignInActivity::class.java)  // Ganti dengan activity yang sesuai
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            dialog.dismiss()
        }
        retryBuilder.setNegativeButton("Tunggu") { dialog, _ ->
            dialog.dismiss()
        }
        retryBuilder.show()
    }

    private fun handleWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.questionnaire)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}


