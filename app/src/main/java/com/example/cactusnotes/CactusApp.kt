package com.example.cactusnotes

import android.app.Application
import com.example.cactusnotes.service.generateApi

class CactusApp : Application() {
    override fun onCreate() {
        super.onCreate()
        generateApi(this)
    }
}