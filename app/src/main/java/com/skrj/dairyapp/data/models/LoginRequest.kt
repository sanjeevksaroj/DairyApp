package com.skrj.dairyapp.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    val phoneNumber: String
)

