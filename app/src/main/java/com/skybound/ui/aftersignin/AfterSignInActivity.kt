package com.skybound.ui.aftersignin

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.skybound.MainViewModel
import com.skybound.R
import com.skybound.databinding.ActivityAfterSignInBinding
import com.skybound.databinding.ActivityMainBinding
import com.skybound.databinding.ItemRoadmapBinding
import com.skybound.ui.questionnaire.QuestionnaireActivity
import com.skybound.ui.settings.SettingsViewModel
import com.skybound.ui.signin.SignInActivity
import com.skybound.ui.utils.ViewModelFactory
import kotlin.math.log

class AfterSignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAfterSignInBinding
    private lateinit var viewmodel: AfterSignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAfterSignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewmodel = ViewModelProvider(this, factory)[AfterSignInViewModel::class.java]

        logout()
        questionnaire()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.aftersignin)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun logout() {
        binding.btnBack.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Konfirmasi Logout")
            builder.setMessage("Anda akan logout, Anda harus login kembali untuk melanjutkan. Apakah Anda yakin?")
            builder.setPositiveButton("Logout") { dialog, _ ->
                // Jika memilih untuk logout, lakukan logout
                viewmodel.logout()
                val intent = Intent(this, SignInActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
                dialog.dismiss()
            }
            builder.setNegativeButton("Batal") { dialog, _ ->
                // Jika memilih batal, dialog ditutup
                dialog.dismiss()
            }
            builder.show()
        }
    }

    private fun questionnaire() {
        binding.btnNext.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Konfirmasi Kuisioner")
            builder.setMessage("Anda akan mengisi Kuisioner, Anda tidak akan bisa kembali sampai menyelesaikan kuisioner Anda. Apakah Anda ingin melanjutkan?")
            builder.setPositiveButton("Kerjakan") { dialog, _ ->
                // Jika memilih untuk melanjutkan, arahkan ke QuestionnaireActivity
                val intent = Intent(this, QuestionnaireActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
                dialog.dismiss()
            }
            builder.setNegativeButton("Tidak") { dialog, _ ->
                // Jika memilih untuk tidak melanjutkan, dialog ditutup
                dialog.dismiss()
            }
            builder.show()
        }
    }
}