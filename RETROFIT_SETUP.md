# Retrofit Integration Guide for DairyApp

## Overview
This guide explains the Retrofit integration for API calls in the DairyApp project.

## What Was Added

### 1. **Dependencies** (`gradle/libs.versions.toml` & `app/build.gradle.kts`)
- **Retrofit 2.9.0** - HTTP client library
- **OkHttp 4.11.0** - HTTP logging interceptor
- **Moshi 1.15.0** - JSON serialization/deserialization

### 2. **Package Structure**

```
data/
├── api/
│   ├── DairyApiService.kt     (API endpoints interface)
│   └── RetrofitClient.kt      (Retrofit configuration)
├── models/
│   ├── LoginRequest.kt
│   ├── LoginResponse.kt
│   ├── OtpModels.kt
│   └── UserProfile.kt
└── repository/
    └── AuthRepository.kt      (Data access layer)
```

## How to Use

### 1. **Configure Base URL**
Edit `RetrofitClient.kt` and set your API base URL:

```kotlin
private const val BASE_URL = "https://your-api-domain.com/v1/"
```

### 2. **Add More API Endpoints**
Edit `DairyApiService.kt` to add new endpoints:

```kotlin
@POST("api/products")
suspend fun getProducts(): ProductResponse

@GET("api/product/{id}")
suspend fun getProductById(
    @Path("id") productId: String
): ProductResponse
```

### 3. **Create Data Models**
Create new model classes in `data/models/`:

```kotlin
@JsonClass(generateAdapter = true)
data class Product(
    val id: String,
    val name: String,
    val price: Double
)
```

### 4. **Add Repository Methods**
Create methods in `AuthRepository.kt` (or new repository classes):

```kotlin
suspend fun getProducts(): Result<ProductResponse> {
    return try {
        val response = apiService.getProducts()
        Result.success(response)
    } catch (e: Exception) {
        Result.failure(Exception("Failed to get products: ${e.message}"))
    }
}
```

### 5. **Use in ViewModel**
Access the repository from ViewModels:

```kotlin
class ProductViewModel : ViewModel() {
    private val repository = AuthRepository()

    fun loadProducts() {
        viewModelScope.launch {
            val result = repository.getProducts()
            result.onSuccess { response ->
                // Update UI
            }
            result.onFailure { exception ->
                // Handle error
            }
        }
    }
}
```

## Updated Files

### LoginViewModel.kt
- Now calls `AuthRepository.login()` instead of simulating delay
- Returns `LoginResponse` with session ID
- Error handling included

### OtpViewModel.kt
- Now calls `AuthRepository.verifyOtp()` for verification
- Returns token on successful verification
- Maintains timer functionality

### NameViewModel.kt
- Now calls `AuthRepository.saveUserProfile()` to save user data
- Supports name, email, and address fields
- Token-based authentication

## Example API Responses

### Login Response
```json
{
  "success": true,
  "message": "OTP sent successfully",
  "sessionId": "session_123456",
  "otpSent": true
}
```

### OTP Verification Response
```json
{
  "success": true,
  "message": "OTP verified",
  "token": "eyJhbGciOiJIUzI1NiIs..."
}
```

### User Profile Response
```json
{
  "success": true,
  "message": "Profile saved successfully",
  "user": {
    "userId": "user_123",
    "name": "John Doe",
    "phoneNumber": "+91...",
    "email": "john@example.com",
    "address": "123 Street, City"
  }
}
```

## Features

✅ **Automatic JSON Serialization** - Moshi handles JSON conversion
✅ **HTTP Logging** - All API calls are logged for debugging
✅ **Error Handling** - Using Kotlin Result type
✅ **Coroutines** - Async API calls with suspend functions
✅ **Repository Pattern** - Clean separation of concerns
✅ **Type Safety** - Kotlin data classes ensure type safety

## Testing

To test the API integration:

1. **Update** `BASE_URL` in `RetrofitClient.kt` with your test server
2. **Mock** the API responses if needed
3. **Check Android Studio Logcat** to see HTTP logs with the logging interceptor

## Security Notes

⚠️ Important security practices:
- Never commit API keys to version control
- Use BuildConfig to store sensitive data
- Implement certificate pinning for production
- Always use HTTPS for API communication
- Store tokens securely using EncryptedSharedPreferences

## Troubleshooting

### "Failed to create service" Error
- Check that `DairyApiService` interface is correctly defined
- Verify `BASE_URL` ends with `/`

### "No adapter found" Error
- Ensure `@JsonClass(generateAdapter = true)` is on your data classes
- Add `KotlinJsonAdapterFactory()` to Moshi builder

### Network Timeout
- Increase timeout in `RetrofitClient.kt`:
  ```kotlin
  .connectTimeout(60, TimeUnit.SECONDS)
  ```

## Next Steps

1. Replace placeholder API endpoints with real endpoints
2. Add authentication with JWT tokens
3. Implement request/response interceptors
4. Add error handling and retry logic
5. Create unit tests for repositories

