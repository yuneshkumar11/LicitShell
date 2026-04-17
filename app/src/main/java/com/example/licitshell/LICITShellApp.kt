package com.example.licitshell

import android.app.Application

class LICITShellApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ThemePreferences.applySavedTheme(this)
    }
}
