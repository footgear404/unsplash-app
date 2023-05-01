package com.semenchuk.unsplash

import android.app.Application
import com.semenchuk.unsplash.di.AppComponent
import com.semenchuk.unsplash.di.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().application(this).build()
    }
}