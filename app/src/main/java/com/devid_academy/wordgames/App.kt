package com.devid_academy.wordgames

import android.app.Application
import com.devid_academy.auth.di.moduleModelUser
import com.devid_academy.common.di.modulePresentationCommon
import com.devid_academy.gamedata.di.moduleModelGamedata
import com.devid_academy.local.moduleModelLocalDB
import com.devid_academy.motus.moduleUiMotus
import com.devid_academy.distant.moduleNetwork
import com.devid_academy.hangman.moduleUiHangman
import com.devid_academy.ui.SharedPrefsManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                modulePresentationCommon,
                moduleNetwork,
                moduleModelLocalDB,

                moduleModelGamedata,
                moduleModelUser,

                moduleUiMotus,
                moduleUiHangman
            )
        }
        SharedPrefsManager.init(this)
    }
}