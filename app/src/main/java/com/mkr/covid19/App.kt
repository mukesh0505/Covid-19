package com.mkr.covid19

import android.app.Application
import com.mkr.covid19.di.ApplicationComponent
import com.mkr.covid19.di.DaggerApplicationComponent

class App : Application() {

    val appComponent: ApplicationComponent = DaggerApplicationComponent.create()

}