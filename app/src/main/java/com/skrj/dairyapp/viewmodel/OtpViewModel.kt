package com.skrj.dairyapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OtpViewModel : ViewModel() {

    var otp = mutableStateOf("")
        private set

    var isLoading = mutableStateOf(false)
        private set

    var timer = mutableStateOf(30)
        private set

    init {
        startTimer()
    }

    fun onOtpChange(value: String) {
        if (value.length <= 6) otp.value = value
    }

    fun verifyOtp(onSuccess: () -> Unit) {

        if (otp.value.length < 6) return

        isLoading.value = true

        viewModelScope.launch {
            delay(1500) // simulate API
            isLoading.value = false
            onSuccess()
        }
    }

    fun resendOtp() {
        timer.value = 30
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (timer.value > 0) {
                delay(1000)
                timer.value--
            }
        }
    }
}