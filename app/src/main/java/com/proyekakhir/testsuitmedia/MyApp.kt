package com.proyekakhir.testsuitmedia

import android.app.Application
import com.proyekakhir.core.di.coreModule
import com.proyekakhir.testsuitmedia.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(
                listOf(
                    viewModule,
                    coreModule
                )
            )
        }
    }
}