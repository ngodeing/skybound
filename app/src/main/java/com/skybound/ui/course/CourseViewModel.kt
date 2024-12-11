package com.skybound.ui.course

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.skybound.data.remote.response.SubCourseItem
import com.skybound.data.user.User
import com.skybound.data.user.UserRepository
import kotlinx.coroutines.launch

class CourseViewModel(private val repository: UserRepository) : ViewModel() {

    private val _subCourses = MutableLiveData<List<SubCourseItem>>()
    val subCourses: LiveData<List<SubCourseItem>> = _subCourses

    fun fetchSubCourses(token: String, roadmapName: String, courseName: String) {
        viewModelScope.launch {
            try {
                val response = repository.getSubCourses(token, roadmapName, courseName)
                _subCourses.value = response.subcourses.map {
                    SubCourseItem(it.subcourseName, it.description)
                }
            } catch (e: Exception) {
                Log.e("CourseViewModel", "Error fetching subcourses: ${e.message}")
            }
        }
    }
    fun getSession(): LiveData<User> {
        return repository.getSession().asLiveData()
    }
}
