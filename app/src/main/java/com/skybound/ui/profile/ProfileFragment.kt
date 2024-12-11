package com.skybound.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.skybound.data.local.entity.UserEntity
import com.skybound.databinding.FragmentProfileBinding
import com.skybound.ui.signin.SignInActivity
import com.skybound.ui.utils.ViewModelFactory
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.getSession().collect { user ->
                val token = user.token
                if (token.isNotEmpty()) {
                    viewModel.fetchUser(token)
                } else {
                    viewModel.getUserLocally("user_id_example")
                }
            }
        }

        observeViewModel()
        logout()
    }

    private fun observeViewModel() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.apply {
                profileName.text = user.username
                profilePoints.text = "${user.userPoint} Poin"
                profileEmail.text = "Email: ${user.email}"
                profileStatus.text = "Status: ${user.status}"
                profileGender.text = "Jenis Kelamin: ${user.gender}"
                profilePhone.text = "No. Telp: ${user.phoneNumber}"
                profileDob.text = "Tgl. Lahir: ${user.dateOfBirth}"
                profileRoadmap.text = "Roadmap: ${user.roadmaps}"
            }
            viewModel.mergeAndSaveUserLocally(
                UserEntity(
                    id = "user_id_example",
                    username = user.username,
                    email = user.email,
                    phoneNumber = user.phoneNumber,
                    dateOfBirth = user.dateOfBirth,
                    gender = user.gender,
                    userPoint = user.userPoint,
                    status = user.status,
                    roadmap = user.roadmaps
                )
            )

        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun logout() {
        binding.logoutButton.setOnClickListener{
            viewModel.logout()
            val intent = Intent(requireContext(), SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            requireActivity().finish()
        }
    }
}