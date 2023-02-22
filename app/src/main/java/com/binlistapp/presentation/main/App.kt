package com.binlistapp.presentation.main

import android.app.Application
import com.binlistapp.di.component.AppComponent
import com.binlistapp.di.component.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)

    }
}