package com.devid_academy.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {

    @POST("/users/guest")
    suspend fun addGuestUser(
        @Body guestUser: CreateUserGuestDto
    ): Response<User>

    @POST("/users/register")
    suspend fun signupUser(
        @Body signupDto: SignupDto
    ): Response<AuthResponse>

    @POST("/users/login")
    suspend fun loginUser(
        @Body loginDto: LoginDto
    ): Response<AuthResponse>

}

