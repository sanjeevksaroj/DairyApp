package com.skrj.dairyapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skrj.dairyapp.data.repository.AuthRepository
import kotlinx.coroutines.launch

class NameViewModel : ViewModel() {

    var name = mutableStateOf("")
        private set

    var isLoading = mutableStateOf(false)
        private set

    var error = mutableStateOf<String?>(null)
        private set

    var token = mutableStateOf<String?>(null)
        private set

    var email = mutableStateOf<String?>(null)
        private set

    var address = mutableStateOf<String?>(null)
        private set

    private val repository = AuthRepository()

    fun onNameChange(value: String) {
        name.value = value
    }

    fun onEmailChange(value: String) {
        email.value = value
    }

    fun onAddressChange(value: String) {
        address.value = value
    }

    fun setToken(authToken: String) {
        token.value = authToken
    }

    /**
     * Save user profile with Retrofit API call
     */
    fun saveName(onSuccess: (name: String) -> Unit) {
        if (name.value.isBlank()) {
            error.value = "Name cannot be empty"
            return
        }

        isLoading.value = true
        error.value = null

        viewModelScope.launch {
            val authToken = token.value ?: return@launch
            
            val result = repository.saveUserProfile(
                token = authToken,
                name = name.value,
                email = email.value,
                address = address.value
            )

            result.onSuccess { response ->
                isLoading.value = false
                if (response.success) {
                    onSuccess(name.value)
                } else {
                    error.value = response.message
                }
            }

            result.onFailure { exception ->
                isLoading.value = false
                error.value = "Failed to save profile: ${exception.message}"
            }
        }
    }

    fun clearError() {
        error.value = null
    }
}