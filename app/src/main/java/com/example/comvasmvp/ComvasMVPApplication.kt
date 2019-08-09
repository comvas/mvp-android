package com.example.comvasmvp

import android.app.Application
import io.realm.Realm

class ComvasMVPApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}