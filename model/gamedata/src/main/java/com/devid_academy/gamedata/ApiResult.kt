package com.devid_academy.gamedata

import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

sealed class ApiResult<out R> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val exception: Throwable, val code: Int? = null) : ApiResult<Nothing>()
}


suspend fun <T : Any> handleApi(execute: suspend () -> Response<T>): ApiResult<T> {
    return try {
        val response = execute()
        val body = response.body()

        if (response.isSuccessful && body != null) {
            ApiResult.Success(data = body)
        } else {
            ApiResult.Error(exception = Throwable(response.message()), code = response.code())
        }
    } catch (e: HttpException) {
        ApiResult.Error(exception = e, code = e.code())
    } catch (e: UnknownHostException) {
        ApiResult.Error(exception = Throwable("No known host"), code = 404)
    } catch (e: Throwable) {
        ApiResult.Error(exception = e)
    }
}
