package com.devid_academy.local.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devid_academy.local.RoleEnum
import java.time.LocalDateTime
import java.util.UUID

@Entity(tableName = "user")
data class UserLocal(
    @PrimaryKey(autoGenerate = false) val id: UUID,
    @ColumnInfo(name = "username") val username: String? = null,
    @ColumnInfo(name = "email") val email: String? = null,
    @ColumnInfo(name = "password") val password: String? = null,
    @ColumnInfo(name = "creation_datetime") val creationDatetime: LocalDateTime,
    @ColumnInfo(name = "registration_datetime") val registrationDatetime: LocalDateTime? = null,
    @ColumnInfo(name = "role") val role: RoleEnum
)