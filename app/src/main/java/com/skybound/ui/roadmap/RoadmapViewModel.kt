package com.skybound.ui.roadmap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RoadmapViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Roadmap Fragment"
    }
    val text: LiveData<String> = _text
}