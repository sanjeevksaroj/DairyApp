package com.skrj.dairyapp.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    val success: Boolean,
    val message: String,
    val sessionId: String? = null,
    val otpSent: Boolean = false
)

