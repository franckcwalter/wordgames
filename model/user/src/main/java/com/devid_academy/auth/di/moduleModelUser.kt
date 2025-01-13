package com.devid_academy.auth.di

import com.devid_academy.auth.UserRepository
import com.devid_academy.auth.UserRepositoryImpl
import com.devid_academy.auth.UserService
import org.koin.dsl.module
import retrofit2.Retrofit

inline fun <reified T> createService(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}

val moduleModelUser = module {

    single { createService<UserService>(get()) }
    single<UserRepository> { UserRepositoryImpl(get(), get()) }

}