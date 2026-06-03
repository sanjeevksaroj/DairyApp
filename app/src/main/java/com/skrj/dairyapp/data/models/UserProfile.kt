package com.skrj.dairyapp.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserProfile(
    val userId: String,
    val name: String,
    val phoneNumber: String,
    val email: String? = null,
    val address: String? = null,
    val profileImage: String? = null
)

@JsonClass(generateAdapter = true)
data class UserProfileRequest(
    val name: String,
    val email: String? = null,
    val address: String? = null
)

@JsonClass(generateAdapter = true)
data class UserProfileResponse(
    val success: Boolean,
    val message: String,
    val user: UserProfile? = null
)

