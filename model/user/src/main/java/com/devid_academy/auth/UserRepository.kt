package com.devid_academy.auth

import android.util.Log
import com.devid_academy.local.LocalDatabase
import com.devid_academy.local.user.UserLocal
import com.devid_academy.ui.SharedPrefsManager
import com.devid_academy.ui.SharedPrefsManager.REFRESH_TOKEN
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import retrofit2.HttpException

interface UserRepository {
    suspend fun checkIfUserExistsLocal(): Boolean
    suspend fun createGuestUserLocal(guestUser: UserLocal): Long
    suspend fun createGuestUserRemote(guestUser: CreateUserGuestDto): ApiResult<User>
    suspend fun signup(signupDto: SignupDto): ApiResult<AuthResponse>
    suspend fun login(loginDto: LoginDto): ApiResult<AuthResponse>

    fun getAccessToken(): String?
    fun setAccessToken(token: String)
    suspend fun refreshToken(): ApiResult<AuthResponse>
    suspend fun validateToken(refreshToken: String): ApiResult<Boolean>
    suspend fun logout(): ApiResult<Unit>
}

class UserRepositoryImpl(
    private val apiService: UserService,
    private val localdb: LocalDatabase
) : UserRepository {

    private val refreshTokenMutex = Mutex()

    @Volatile
    private var accessToken: String? = null

    override fun getAccessToken(): String? {
        return accessToken
    }
    override fun setAccessToken(token: String) {
        accessToken = token
    }


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
                setAccessToken(response.data.accessToken)
                SharedPrefsManager[REFRESH_TOKEN] = response.data.refreshToken
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
                setAccessToken(response.data.accessToken)
                SharedPrefsManager[REFRESH_TOKEN] = response.data.refreshToken
            }
            response
        } catch (e: HttpException) {
            ApiResult.Error(e, e.code())
        } catch (e: Throwable) {
            ApiResult.Error(e)
        }
    }

    override suspend fun refreshToken(): ApiResult<AuthResponse> {
        return refreshTokenMutex.withLock {
            try {
                val resfreshToken: String = SharedPrefsManager[REFRESH_TOKEN]
                val response = handleApi { apiService.refreshToken(resfreshToken) }

                if (response is ApiResult.Success) {
                    setAccessToken(response.data.accessToken)
                    SharedPrefsManager[REFRESH_TOKEN] = response.data.refreshToken
                }
                response
            } catch (e: Exception) {
                ApiResult.Error(e)
            }
        }
    }

    override suspend fun validateToken(refreshToken: String): ApiResult<Boolean> {
        return try {
            val response = handleApi { apiService.validateToken(refreshToken) }
            Log.e("validateToken","validateToken : $response")

            response
        } catch (e: HttpException) {
            ApiResult.Error(e, e.code())
        } catch (e: Throwable) {
            ApiResult.Error(e)
        }
    }

    override suspend fun logout(): ApiResult<Unit> {
        val refreshToken: String = SharedPrefsManager[REFRESH_TOKEN]  // Get token first
        
        // Clear tokens
        setAccessToken("")  // or null
        SharedPrefsManager[REFRESH_TOKEN] = null
    
        return try {
            val response = handleApi { apiService.logout(refreshToken) }  // Use the saved token
            Log.e("validateToken","validateToken : $response")
            response
        } catch (e: HttpException) {
            ApiResult.Error(e, e.code())
        } catch (e: Throwable) {
            ApiResult.Error(e)
        }
    }


}