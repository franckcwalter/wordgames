package com.devid_academy.auth

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.util.UUID

data class CreateUserGuestDto (
    val id: UUID,

    @SerializedName("creation_datetime")
    val creationDateTime: LocalDateTime
)
