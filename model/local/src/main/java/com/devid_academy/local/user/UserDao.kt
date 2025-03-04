package com.devid_academy.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.UUID

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGuestUser(guestUser: UserLocal): Long

    @Query("SELECT COUNT(*) FROM user")
    suspend fun getGuestUserCount(): Long

    @Query("SELECT id FROM user LIMIT 1")
    suspend fun getGuestUserId(): UUID

}