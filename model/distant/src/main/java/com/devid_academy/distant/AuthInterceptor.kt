package com.devid_academy.distant

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import com.devid_academy.auth.UserRepository

class AuthInterceptor(private val userRepository: Lazy<UserRepository>) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val token = try {
            userRepository.value.getAccessToken()
        } catch (e: Exception) {
            Log.e("koin", e.toString())
            null
        }

        val authenticatedRequest = if (!token.isNullOrEmpty()) {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }

        return chain.proceed(authenticatedRequest)
    }
}

/*
class TokenInterceptor(private val userRepository: Lazy<UserRepository>) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)
        // Check if the response indicates that the access token is expired
        if (response.code == 401) {
            // Call the refresh token API to obtain a new access token
            val newAccessToken = callRefreshTokenAPI()
            // Create a new request with the updated access token
            val newRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $newAccessToken")
                .build()
            // Retry the request with the new access token
            return chain.proceed(newRequest)
        }
        return response
    }
    private fun callRefreshTokenAPI(): String {
        // Make the API call to refresh the access token using the refresh token
        // and return the new access token
        // … implementation for calling the API and parsing the response …
        return ""
    }
}
*/