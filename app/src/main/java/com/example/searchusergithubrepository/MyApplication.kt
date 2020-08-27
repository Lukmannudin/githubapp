package com.example.searchusergithubrepository

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        Fresco.initialize(this)
    }
}