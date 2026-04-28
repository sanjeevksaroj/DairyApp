package com.skrj.dairyapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var phone = mutableStateOf("")
        private set

    var isLoading = mutableStateOf(false)
        private set

    fun onPhoneChange(value: String) {
        phone.value = value
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun onLoginClicked(onSuccess: () -> Unit) {

        if (phone.value.length < 10) return

        isLoading.value = true

        // Simulate API / Firebase delay
        viewModelScope.launch {
            delay(1500)
            isLoading.value = false
            onSuccess()
        }
    }
}