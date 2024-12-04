package com.skybound.ui.signup

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.skybound.R
import com.skybound.databinding.ActivitySignUpBinding
import com.skybound.ui.utils.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var signupViewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        signupViewModel = ViewModelProvider(this, factory)[SignUpViewModel::class.java]

        binding.etDateOfBirth.setOnClickListener{
            showDatePickerDialog()
        }

        val genders = arrayOf("Male", "Female")
        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGender.adapter = genderAdapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signup)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupAction()
        observeViewModel()
    }

    private fun setupAction() {
        binding.btnSignUp.setOnClickListener {
            val name = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val phoneNumber = binding.etPhoneNumber.text.toString().trim()
            val dateOfBirth = binding.etDateOfBirth.text.toString().trim()
            val gender = binding.spinnerGender.selectedItem.toString()
            val status = binding.etStatus.text.toString().trim()

            if (name.isEmpty()) {
                binding.etUsername.error = "Nama tidak boleh kosong"
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                binding.etEmail.error = "Email tidak boleh kosong"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.etPassword.error = "Password tidak boleh kosong"
                return@setOnClickListener
            }

            if (phoneNumber.isEmpty()) {
                binding.etPhoneNumber.error = "Nomor telepon tidak boleh kosong"
                return@setOnClickListener
            }

            if (dateOfBirth.isEmpty()) {
                binding.etDateOfBirth.error = "Tanggal lahir telepon tidak boleh kosong"
                return@setOnClickListener
            }

            if (status.isEmpty()) {
                binding.etStatus.error = "Tanggal lahir telepon tidak boleh kosong"
                return@setOnClickListener
            }

            // Call signup ViewModel to submit to API
            signupViewModel.signup(name, email, password, phoneNumber, dateOfBirth, gender, status)
        }
    }

    private fun observeViewModel() {
        signupViewModel.signupResult.observe(this) { result ->
            result.onSuccess { response ->
                AlertDialog.Builder(this).apply {
                    setTitle("Berhasil!")
                    setMessage("Akun berhasil dibuat.")
                    setPositiveButton("Lanjutkan") { _, _ -> finish() }
                    create()
                    show()
                }
            }

            result.onFailure { error ->
                binding.tvErrorMessage.text = error.message
                binding.tvErrorMessage.visibility = View.VISIBLE
            }
        }
    }


    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance().apply {
                    set(Calendar.YEAR, selectedYear)
                    set(Calendar.MONTH, selectedMonth)
                    set(Calendar.DAY_OF_MONTH, selectedDay)
                }

                // Format the selected date
                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                binding.etDateOfBirth.setText(dateFormat.format(selectedDate.time))
            },
            year,
            month,
            dayOfMonth
        )

        // Show the DatePickerDialog
        datePickerDialog.show()
    }
}