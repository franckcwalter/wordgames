package com.devid_academy.auth

import com.devid_academy.local.RoleEnum
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.util.UUID


data class User(
    val id: UUID,
    val username: String?,
    val email: String?,
    @SerializedName("creation_datetime")
    val creationDatetime: LocalDateTime,
    @SerializedName("registration_datetime")
    val registrationDatetime: LocalDateTime?,
    val role: RoleEnum
)
