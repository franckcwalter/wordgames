package com.devid_academy.common.di

import com.devid_academy.common.home.HomeViewModel
import com.devid_academy.common.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modulePresentationCommon = module {

    viewModel { SplashViewModel() }
    viewModel { HomeViewModel() }

}