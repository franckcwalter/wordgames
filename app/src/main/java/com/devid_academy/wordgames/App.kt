package com.devid_academy.wordgames

import android.app.Application
import com.devid_academy.common.di.modulePresentationCommon
import com.devid_academy.gamedata.di.moduleModelGamedata
import com.devid_academy.local.moduleModelLocalDB
import com.devid_academy.motus.moduleUiMotus
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
                moduleModelGamedata,
                moduleUiMotus,
                moduleModelLocalDB
            )
        }
    }
}