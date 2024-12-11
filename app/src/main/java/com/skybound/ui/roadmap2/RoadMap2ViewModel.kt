package com.skybound.ui.roadmap2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skybound.data.Roadmap2Item
import com.skybound.data.remote.response.Roadmap2Request
import com.skybound.data.remote.response.Roadmap2Response
import com.skybound.data.user.User
import com.skybound.data.user.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class RoadMap2ViewModel(private val repository: UserRepository): ViewModel() {
    private val _roadmapPostStatus = MutableLiveData<Result<Roadmap2Response>>()
    val roadmapPostStatus: LiveData<Result<Roadmap2Response>> get() = _roadmapPostStatus

    fun postRoadmap(token: String,roadmapId: String, deadline: String) : LiveData<Result<Roadmap2Response>> {
        viewModelScope.launch {
            try {
                val response = repository.saveRoadmap(token, Roadmap2Request(roadmapId))
                val roadmap2item = Roadmap2Item(
                    title = roadmapId,
                    date = deadline,
                    isRoadmap2 = true
                )
                repository.saveRoadmap(roadmap2item)
                _roadmapPostStatus.postValue(Result.Success(response))
            } catch (e: Exception) {
                _roadmapPostStatus.value = Result.Error("Network Error: ${e.localizedMessage}")
            }
        }
        return roadmapPostStatus
    }

    fun getSession(): Flow<User> {
        return repository.getSession()
    }

}

sealed class Result<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Result<T>(data)
    class Error<T>(message: String, data: T? = null) : Result<T>(data, message)
}