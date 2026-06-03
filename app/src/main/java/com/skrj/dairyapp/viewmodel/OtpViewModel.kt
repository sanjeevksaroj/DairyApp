package com.skrj.dairyapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skrj.dairyapp.data.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OtpViewModel : ViewModel() {

    var otp = mutableStateOf("")
        private set

    var isLoading = mutableStateOf(false)
        private set

    var timer = mutableStateOf(30)
        private set

    var error = mutableStateOf<String?>(null)
        private set

    var sessionId = mutableStateOf<String?>(null)
        private set

    var token = mutableStateOf<String?>(null)
        private set

    private val repository = AuthRepository()

    init {
        startTimer()
    }

    fun onOtpChange(value: String) {
        if (value.length <= 6) otp.value = value
    }

    fun setSessionId(id: String) {
        sessionId.value = id
    }

    /**
     * Verify OTP with Retrofit API call
     */
    fun verifyOtp(onSuccess: () -> Unit) {
        if (otp.value.length < 6) {
            error.value = "Please enter complete OTP"
            return
        }

        isLoading.value = true
        error.value = null

        viewModelScope.launch {
            val sessionIdValue = sessionId.value ?: return@launch
            val result = repository.verifyOtpAndLogin(sessionIdValue, otp.value)

            result.onSuccess { appToken ->
                isLoading.value = false
                token.value = appToken
                onSuccess()
            }

            result.onFailure { exception ->
                isLoading.value = false
                error.value = "OTP verification failed: ${exception.message}"
            }
        }
    }

    fun resendOtp() {
        timer.value = 30
        startTimer()
    }

    fun clearError() {
        error.value = null
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