package com.skrj.dairyapp.viewmodel

import android.app.Activity
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skrj.dairyapp.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var phone = mutableStateOf("")
        private set

    var isLoading = mutableStateOf(false)
        private set

    var error = mutableStateOf<String?>(null)
        private set

    var sessionId = mutableStateOf<String?>(null)
        private set

    private val repository = AuthRepository()

    fun onPhoneChange(value: String) {
        phone.value = value
    }

    /**
     * Perform login with Retrofit API call
     */
    fun onLoginClicked(activity: Activity, onSuccess: () -> Unit) {
        if (phone.value.length < 10) {
            error.value = "Phone number must be at least 10 digits"
            return
        }

        isLoading.value = true
        error.value = null

        viewModelScope.launch {
            val result = repository.sendOtp(activity, phone.value)

            result.onSuccess { verificationId ->
                isLoading.value = false
                sessionId.value = verificationId
                onSuccess()
            }

            result.onFailure { exception ->
                isLoading.value = false
                error.value = "Failed to send OTP: ${exception.message}"
            }
        }
    }

    fun clearError() {
        error.value = null
    }
}