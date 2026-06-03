package com.skrj.dairyapp.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OtpVerifyRequest(
    val sessionId: String,
    val otp: String
)

@JsonClass(generateAdapter = true)
data class OtpVerifyResponse(
    val success: Boolean,
    val message: String,
    val token: String? = null
)

