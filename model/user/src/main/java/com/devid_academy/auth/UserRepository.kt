package com.devid_academy.auth

import android.util.Log
import com.devid_academy.distant.ApiResult
import com.devid_academy.distant.handleApi
import com.devid_academy.local.user.UserLocal
import com.devid_academy.local.LocalDatabase
import retrofit2.HttpException

interface UserRepository {
    suspend fun checkIfUserExistsLocal(): Boolean
    suspend fun createGuestUserLocal(guestUser: UserLocal): Long
    suspend fun createGuestUserRemote(guestUser: CreateUserGuestDto): ApiResult<User>
}

class UserRepositoryImpl(
    private val apiService: UserService,
    private val localdb: LocalDatabase
) : UserRepository {

    override suspend fun checkIfUserExistsLocal(): Boolean {
        return localdb.userDao().getGuestUser() > 0L
    }

    override suspend fun createGuestUserLocal(guestUser: UserLocal): Long {
        return localdb.userDao().insertGuestUser(guestUser)
    }

    override suspend fun createGuestUserRemote(guestUser: CreateUserGuestDto): ApiResult<User> {
        return try {
            val response = handleApi { apiService.addGuestUser(guestUser) }
            Log.e("GameDataRepositoryImpl","fetchGamesWithData() response : $response")

            if (response is ApiResult.Success) {

            }
            response
        } catch (e: HttpException) {
            ApiResult.Error(e, e.code())
        } catch (e: Throwable) {
            ApiResult.Error(e)
        }
    }

}