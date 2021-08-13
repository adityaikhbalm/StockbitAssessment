package com.stockbit.hiring

import android.app.Application
import com.stockbit.hiring.di.appComponent
import org.koin.core.context.startKoin

open class App: Application() {
    override fun onCreate() {
        super.onCreate()
        configureDi()
    }

    // CONFIGURATION ---
    open fun configureDi() =
        startKoin {
            provideComponent()
        }

    // PUBLIC API ---
    open fun provideComponent() = appComponent
}