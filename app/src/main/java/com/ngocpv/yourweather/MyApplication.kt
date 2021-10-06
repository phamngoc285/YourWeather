package com.ngocpv.yourweather

import android.app.Application
import com.ngocpv.yourweather.di.getDI
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(getDI())
        }
    }
}