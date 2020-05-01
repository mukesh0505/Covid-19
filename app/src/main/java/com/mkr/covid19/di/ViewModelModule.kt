package com.mkr.covid19.di

import com.mkr.covid19.repository.CovidService
import com.mkr.covid19.viewModel.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun provideMainViewModel(service: CovidService): MainViewModel {
        return MainViewModel(service)
    }
}