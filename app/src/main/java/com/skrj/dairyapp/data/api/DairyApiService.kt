package com.skrj.dairyapp.data.api

import com.skrj.dairyapp.data.models.LoginRequest
import com.skrj.dairyapp.data.models.LoginResponse
import com.skrj.dairyapp.data.models.OtpVerifyRequest
import com.skrj.dairyapp.data.models.OtpVerifyResponse
import com.skrj.dairyapp.data.models.UserProfileRequest
import com.skrj.dairyapp.data.models.UserProfileResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Header

interface DairyApiService {

    /**
     * Send OTP to phone number
     */
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    /**
     * Verify OTP
     */
    @POST("auth/verify-otp")
    suspend fun verifyOtp(
        @Body request: OtpVerifyRequest
    ): OtpVerifyResponse

    /**
     * Exchange Firebase ID token with backend to create or retrieve app session/token
     * The backend should verify the Firebase token and respond with an app token in OtpVerifyResponse.token
     */
    @POST("auth/firebase-login")
    suspend fun firebaseLogin(
        @Header("Authorization") idToken: String
    ): OtpVerifyResponse

    /**
     * Save user profile
     */
    @POST("user/profile")
    suspend fun saveUserProfile(
        @Body request: UserProfileRequest,
        @Header("Authorization") token: String
    ): UserProfileResponse
}

