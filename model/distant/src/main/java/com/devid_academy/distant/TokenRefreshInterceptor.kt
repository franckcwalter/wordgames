import com.devid_academy.auth.ApiResult
import com.devid_academy.auth.UserRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class TokenRefreshInterceptor(
    private val userRepository: Lazy<UserRepository>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)

        if (response.code == 401) {
            response.close()

            val refreshResult = runBlocking { userRepository.value.refreshToken() }

            if (refreshResult is ApiResult.Success) {
                val newAccessToken = refreshResult.data.accessToken

                val newRequest = originalRequest.newBuilder()
                    .removeHeader("Authorization")
                    .addHeader("Authorization", "Bearer $newAccessToken")
                    .build()

                return chain.proceed(newRequest)
            }
        }
        return response
    }
}
