package com.skybound.ui.signup

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
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
            val confPassword = binding.confPassword.text.toString().trim()
            val phoneNumber = binding.etPhoneNumber.text.toString().trim()
            val dateOfBirth = binding.etDateOfBirth.text.toString().trim()
            val gender = binding.spinnerGender.selectedItem.toString()
            val status = binding.etStatus.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phoneNumber.isEmpty() || dateOfBirth.isEmpty() || status.isEmpty()) {
                Toast.makeText(this, "Harap lengkapi semua form", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confPassword) {
                Toast.makeText(this, "Password dan Konfirmasi Password tidak sama", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Call signup ViewModel to submit to API
            signupViewModel.requestOTP(email)
        }
        binding.btnVerifyOtp.setOnClickListener {
            val otp = binding.etOtp.text.toString().trim()

            if (otp.isEmpty()) {
                Toast.makeText(this, "Masukkan OTP yang valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val email = binding.etEmail.text.toString().trim()
            signupViewModel.verifyOTP(email, otp)
        }
    }

    private fun observeViewModel() {
        signupViewModel.otpRequestResult.observe(this) { result ->
            result.onSuccess {
                Toast.makeText(this, "OTP berhasil dikirim ke Email", Toast.LENGTH_SHORT).show()
                binding.otpLayout.visibility = View.VISIBLE
                binding.tvOtpPrompt.visibility = View.VISIBLE
                binding.btnVerifyOtp.visibility = View.VISIBLE
                binding.tvMasuk.text = "Isi OTP"
                binding.tvDescSignin.text = "Silahkan masukkan OTP anda, kode OTP anda terkirim di email"
                binding.tvUsername.visibility = View.INVISIBLE
                binding.tvEmailSignup.visibility = View.INVISIBLE
                binding.tvPasswordSignup.visibility = View.INVISIBLE
                binding.tvConfirmPw.visibility = View.INVISIBLE
                binding.tvPekerjaan.visibility = View.INVISIBLE
                binding.tvTelepon.visibility = View.INVISIBLE
                binding.tvBirthdate.visibility = View.INVISIBLE
                binding.tvGender.visibility = View.INVISIBLE
                binding.edtUsername.visibility = View.INVISIBLE
                binding.edtEmail.visibility = View.INVISIBLE
                binding.edtPassword.visibility = View.INVISIBLE
                binding.edtconfPassword.visibility = View.INVISIBLE
                binding.edtStatus.visibility = View.INVISIBLE
                binding.edtPhoneNumber.visibility = View.INVISIBLE
                binding.edtDateOfBirth.visibility = View.INVISIBLE
                binding.spinnerGender.visibility = View.INVISIBLE
                binding.btnSignUp.visibility = View.INVISIBLE


            }

            result.onFailure { error ->
                Toast.makeText(this, "Gagal mengirim OTP: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
        signupViewModel.otpVerifyResult.observe(this) { result ->
            result.onSuccess {
                // OTP verified, proceed with sign up
                val name = binding.etUsername.text.toString().trim()
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()
                val phoneNumber = binding.etPhoneNumber.text.toString().trim()
                val dateOfBirth = binding.etDateOfBirth.text.toString().trim()
                val gender = binding.spinnerGender.selectedItem.toString()
                val status = binding.etStatus.text.toString().trim()

                signupViewModel.signup(name, email, password, phoneNumber, dateOfBirth, gender, status)
                signupViewModel.signupResult.observe(this) {

                }
            }

            result.onFailure { error ->
                Toast.makeText(this, "Verifikasi OTP gagal: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
        signupViewModel.signupResult.observe(this) { result ->
            result.onSuccess {
                    AlertDialog.Builder(this).apply {
                    setTitle("Berhasil!")
                    setMessage("Akun berhasil dibuat.")
                    setPositiveButton("Lanjutkan") { _, _ -> finish() }
                    create()
                    show()
                }
            }

            result.onFailure { error ->
                Toast.makeText(this, "Pendaftaran gagal: ${error.message}", Toast.LENGTH_SHORT).show()
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