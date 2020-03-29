package com.olderwold.profile

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class ProfileApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}
