package com.skybound.ui.signin

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.skybound.MainActivity
import com.skybound.R
import com.skybound.databinding.ActivitySignInBinding
import com.skybound.ui.signup.SignUpActivity
import com.skybound.ui.utils.ViewModelFactory

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var loginViewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvSignUp.text = Html.fromHtml("<u>${getString(R.string.signup_create_signin)}</u>")

        val factory = ViewModelFactory.getInstance(this)
        loginViewModel = ViewModelProvider(this, factory)[SignInViewModel::class.java]

        signUp()
        setupAction()
        observeViewModel()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signin)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (binding.etEmail.error != null || binding.etPassword.error != null) {
                Toast.makeText(this, "Masukkan email dan password yang valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            loginViewModel.login(email, password)
        }
    }

    private fun signUp() {
        binding.tvSignUp.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        // Observe hasil login dari ViewModel
        loginViewModel.loginResult.observe(this) { result ->
            result.onSuccess {
                binding.tvErrorMessage.visibility = View.GONE
                Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                // Setelah login berhasil, arahkan ke MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Tutup SignInActivity setelah berhasil
            }

            result.onFailure { error ->
                Toast.makeText(this, "Login Gagal, Coba Lagi", Toast.LENGTH_SHORT).show()
            }
        }
    }
}