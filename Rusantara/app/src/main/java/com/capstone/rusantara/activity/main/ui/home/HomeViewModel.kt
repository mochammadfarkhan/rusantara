package com.capstone.rusantara.activity.main.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "take a photo, upload, and the info will come out"
    }
    val text: LiveData<String> = _text
}