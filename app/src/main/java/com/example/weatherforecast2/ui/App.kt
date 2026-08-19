package com.example.weatherforecast2.ui

import android.app.Application
import com.example.weatherforecast2.di.interactorModule
import com.example.weatherforecast2.di.repositoryModule
import com.example.weatherforecast2.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                repositoryModule,
                interactorModule,
                viewModelModule
            )
        }
    }
}
