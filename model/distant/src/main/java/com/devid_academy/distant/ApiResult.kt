package com.devid_academy.distant

import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

/*
sealed class ApiResult<out R> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val exception: Throwable, val code: Int? = null) : ApiResult<Nothing>()
}*/

data class ApiErrorDetail(
    val code: String,
    val message: String
)

data class ApiErrorResponse(
    val detail: ApiErrorDetail
)

sealed class ApiResult<out R> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(
        val exception: Throwable,
        val httpCode: Int? = null,
        val errorCode: String? = null,
        val errorMessage: String? = null
    ) : ApiResult<Nothing>()
}

suspend fun <T : Any> handleApi(execute: suspend () -> Response<T>): ApiResult<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            ApiResult.Success(data = body)
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = try {
                Gson().fromJson(errorBody, ApiErrorResponse::class.java)
            } catch (e: Exception) {
                null
            }

            ApiResult.Error(
                exception = Throwable(response.message()),
                httpCode = response.code(),
                errorCode = errorResponse?.detail?.code,
                errorMessage = errorResponse?.detail?.message
            )
        }
    } catch (e: HttpException) {
        val errorBody = e.response()?.errorBody()?.string()
        val errorResponse = try {
            Gson().fromJson(errorBody, ApiErrorResponse::class.java)
        } catch (ex: Exception) {
            null
        }

        ApiResult.Error(
            exception = e,
            httpCode = e.code(),
            errorCode = errorResponse?.detail?.code,
            errorMessage = errorResponse?.detail?.message
        )
    } catch (e: UnknownHostException) {
        ApiResult.Error(
            exception = Throwable("No known host"),
            httpCode = 404
        )
    } catch (e: Throwable) {
        ApiResult.Error(exception = e)
    }
}