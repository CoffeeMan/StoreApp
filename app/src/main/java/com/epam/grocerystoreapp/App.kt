package com.epam.grocerystoreapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object{
        const val PACKAGE_URI_SCHEME = "package"
    }

}
