package com.devid_academy.common.di

import com.devid_academy.common.auth.login.LoginViewModel
import com.devid_academy.common.auth.singup.SignupViewModel
import com.devid_academy.common.game_base.GameBaseViewModel
import com.devid_academy.common.home.HomeViewModel
import com.devid_academy.common.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modulePresentationCommon = module {

    viewModel { SplashViewModel(get(), get()) }
    viewModel { HomeViewModel() }
    viewModel { GameBaseViewModel(get()) }

    viewModel { LoginViewModel(get()) }
    viewModel { SignupViewModel(get()) }

}