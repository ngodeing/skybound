package com.skybound.ui.roadmap

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.skybound.data.RoadmapItem
import com.skybound.data.user.User
import com.skybound.data.user.UserRepository
import kotlinx.coroutines.launch

class RoadmapViewModel(private val repository: UserRepository) : ViewModel() {

    private val _courses = MutableLiveData<List<RoadmapItem>>()
    val courses: LiveData<List<RoadmapItem>> = _courses

    fun getSession(): LiveData<User> {
        return repository.getSession().asLiveData()
    }

    fun fetchCourses(token: String) {
        viewModelScope.launch {
            try {
                val courseList = repository.getCourses(token).map {
                    RoadmapItem(it.courseName, "Unknown Date", "0")
                }
                _courses.postValue(courseList)
            } catch (e: Exception) {
                Log.e("RoadmapViewModel", "Error fetching courses: ${e.message}")
            }
        }
    }
}
