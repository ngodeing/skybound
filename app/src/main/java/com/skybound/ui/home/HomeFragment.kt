package com.skybound.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.skybound.databinding.FragmentHomeBinding
import com.skybound.ui.utils.ViewModelFactory
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.getSession().collect { user ->
                val token = user.token
                if (token.isNotEmpty()) {
                    viewModel.fetchUserStatus(token)
                } else {
                    Toast.makeText(requireContext(), "Token tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.userStatus.observe(viewLifecycleOwner) { userStatus ->
            binding.apply {
                userName.text = userStatus.username
                progressPoints.text = "${userStatus.userPoint} Poin"
                progressStreak.text = "${userStatus.userStreak} Days Streak"
                progressCoursesLeft.text = "${userStatus.coursesLeft} Course Left"
                progressPercentage.text = "${0}%"
                progressIntro.text = userStatus.onCourse
                progressTitle.text = userStatus.onCourse
                progressDescription.text = userStatus.onCourse
                onScheduleText.text = userStatus.courseStatus
                progressDaysLeft.text = "${userStatus.deadlineLeft} Days Left"
            }
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
}