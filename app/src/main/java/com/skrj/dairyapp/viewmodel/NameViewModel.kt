package com.skrj.dairyapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NameViewModel : ViewModel() {

    var name = mutableStateOf("")
        private set

    var isLoading = mutableStateOf(false)
        private set

    fun onNameChange(value: String) {
        name.value = value
    }

    fun saveName(onSuccess: () -> Unit) {

        if (name.value.isBlank()) return

        isLoading.value = true

        viewModelScope.launch {
            delay(1000) // simulate API
            isLoading.value = false
            onSuccess()
        }
    }
}