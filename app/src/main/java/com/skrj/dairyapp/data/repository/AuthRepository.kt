package com.skrj.dairyapp.data.repository

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.skrj.dairyapp.data.api.RetrofitClient
import com.skrj.dairyapp.data.models.OtpVerifyResponse
import com.skrj.dairyapp.data.models.UserProfileRequest
import com.skrj.dairyapp.data.models.UserProfileResponse
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import java.util.concurrent.TimeUnit

/**
 * AuthRepository now integrates Firebase Phone Authentication for sending and verifying OTPs.
 * After successful Firebase authentication it exchanges the Firebase ID token with the backend
 * to obtain the application's auth token (returned in OtpVerifyResponse.token).
 */
class AuthRepository {

    private val apiService = RetrofitClient.apiService
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun sendOtp(activity: Activity, phoneNumber: String, timeoutSeconds: Long = 60L): Result<String> {
        return try {
            val verificationId = suspendCancellableCoroutine<String> { cont ->
                val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        // Auto-retrieval; we don't resume here because app expects explicit code entry.
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        if (!cont.isCompleted) cont.resumeWithException(e)
                    }

                    override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                        if (!cont.isCompleted) cont.resume(verificationId)
                    }
                }

                val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                    .setPhoneNumber(phoneNumber)
                    .setTimeout(timeoutSeconds, TimeUnit.SECONDS)
                    .setActivity(activity)
                    .setCallbacks(callbacks)
                    .build()

                PhoneAuthProvider.verifyPhoneNumber(options)
            }

            Result.success(verificationId)
        } catch (e: Exception) {
            Result.failure(Exception("Failed to send OTP: ${e.message}"))
        }
    }

    /**
     * Verify OTP using Firebase, then exchange Firebase ID token with backend to obtain
     * app-specific token. Returns backend token as String (if any).
     */
    suspend fun verifyOtpAndLogin(verificationId: String, code: String): Result<String> {
        return try {
            val credential = PhoneAuthProvider.getCredential(verificationId, code)

            // Sign in with credential
            val authResult = suspendCancellableCoroutine<com.google.firebase.auth.AuthResult> { cont ->
                firebaseAuth.signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val result = task.result
                            if (!cont.isCompleted) cont.resume(result)
                        } else {
                            if (!cont.isCompleted) cont.resumeWithException(task.exception ?: Exception("Sign-in failed"))
                        }
                    }
            }

            // Get Firebase ID token
            val idToken = suspendCancellableCoroutine<String> { cont ->
                val user = firebaseAuth.currentUser
                if (user == null) {
                    cont.resumeWithException(IllegalStateException("No signed-in user after verification"))
                } else {
                    user.getIdToken(true)
                        .addOnCompleteListener { t ->
                            if (t.isSuccessful) {
                                val token = t.result?.token
                                if (token != null) cont.resume(token) else cont.resumeWithException(IllegalStateException("Firebase token was null"))
                            } else {
                                cont.resumeWithException(t.exception ?: Exception("Failed to get ID token"))
                            }
                        }
                }
            }

            // Exchange with backend
            val response: OtpVerifyResponse = apiService.firebaseLogin("Bearer $idToken")
            if (response.success && response.token != null) {
                Result.success(response.token)
            } else {
                Result.failure(Exception("Backend login failed: ${response.message}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("OTP verification/Login failed: ${e.message}"))
        }
    }

    /**
     * Save user profile using backend API. Token should be the app token returned by backend
     * (not the Firebase ID token).
     */
    suspend fun saveUserProfile(
        token: String,
        name: String,
        email: String? = null,
        address: String? = null
    ): Result<UserProfileResponse> {
        return try {
            val request = UserProfileRequest(
                name = name,
                email = email,
                address = address
            )
            val response = apiService.saveUserProfile(request, "Bearer $token")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(Exception("Profile save failed: ${e.message}"))
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }
}

