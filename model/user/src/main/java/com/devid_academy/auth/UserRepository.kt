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
    suspend fun signup(signupDto: SignupDto): ApiResult<AuthResponse>
    suspend fun login(loginDto: LoginDto): ApiResult<AuthResponse>
}

class UserRepositoryImpl(
    private val apiService: UserService,
    private val localdb: LocalDatabase
) : UserRepository {

    override suspend fun checkIfUserExistsLocal(): Boolean {
        return localdb.userDao().getGuestUserCount() > 0L
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

    override suspend fun signup(signupDto: SignupDto): ApiResult<AuthResponse> {

        signupDto.id = localdb.userDao().getGuestUserId()

        Log.e("Signup", signupDto.toString())


        return try {
            val response = handleApi { apiService.signupUser(signupDto) }
            Log.e("Signup","apiService.signupUser response : $response")

            if (response is ApiResult.Success) {

            }
            response
        } catch (e: HttpException) {
            ApiResult.Error(e, e.code())
        } catch (e: Throwable) {
            ApiResult.Error(e)
        }

    }

    override suspend fun login(loginDto: LoginDto): ApiResult<AuthResponse> {

        Log.e("login", loginDto.toString())


        return try {
            val response = handleApi { apiService.loginUser(loginDto) }
            Log.e("login","apiService.signupUser response : $response")

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