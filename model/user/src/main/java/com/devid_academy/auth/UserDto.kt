package com.devid_academy.auth

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.util.UUID

data class CreateUserGuestDto (
    val id: UUID,

    @SerializedName("creation_datetime")
    val creationDateTime: LocalDateTime
)

data class SignupDto(
    var id: UUID?,
    val username: String,
    val email: String,
    val password: String
)

data class LoginDto(
    val email: String,
    val password: String
)

data class AuthResponse(
    val user: User,
    @SerializedName("access_token")
    val accessToken: String
)
