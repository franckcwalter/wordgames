package com.devid_academy.gamedata.di

import com.devid_academy.gamedata.GameDataRepository
import com.devid_academy.gamedata.GameDataRepositoryImpl
import com.devid_academy.gamedata.GameDataService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

inline fun <reified T> createService(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}
val moduleModelGamedata = module {

    single { createService<GameDataService>(get()) }
    single<GameDataRepository> { GameDataRepositoryImpl(get(), get()) }

    single { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY } }
    single {
        OkHttpClient.Builder().apply {
            addInterceptor(get<HttpLoggingInterceptor>())
            callTimeout(10, TimeUnit.SECONDS)
        }.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8001")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}