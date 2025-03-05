package com.devid_academy.distant

import android.media.session.MediaSession.Token
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@RequiresApi(Build.VERSION_CODES.O)
val moduleNetwork = module {

   //  single<AuthInterceptor> { AuthInterceptor(get()) }

    single<AuthInterceptor> {
        AuthInterceptor(
            userRepository = lazy { get() }
        )
    }

    single { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY } }

    single {
        OkHttpClient.Builder().apply {
            // addInterceptor(get<AuthInterceptor>())
            addInterceptor(get<AuthInterceptor>())
            addInterceptor(get<HttpLoggingInterceptor>())
            callTimeout(10, TimeUnit.SECONDS)
        }.build()
    }

    single {

        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
            .create()

        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8001")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}
