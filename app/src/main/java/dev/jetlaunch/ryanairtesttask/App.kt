package dev.jetlaunch.ryanairtesttask

import android.app.Application
import dev.jetlaunch.ryanairtesttask.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(mainModule)
        }
    }
}