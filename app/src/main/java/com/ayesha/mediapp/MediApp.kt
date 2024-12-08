package com.ayesha.mediapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MediApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}