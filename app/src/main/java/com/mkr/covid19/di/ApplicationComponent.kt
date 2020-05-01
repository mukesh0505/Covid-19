package com.mkr.covid19.di

import com.mkr.covid19.MainActivity
import dagger.Component

@Component(modules = [NetworkModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(activity: MainActivity)
}