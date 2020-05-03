package com.mkr.covid19.di

import com.mkr.covid19.repository.CovidService
import com.mkr.covid19.utils.COVID_19_INDIA_BASE_URL
import com.mkr.covid19.viewModel.IndiaViewModel
import com.mkr.covid19.viewModel.MainViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
class ViewModelModule {

    @Provides
    fun provideMainViewModel(service: CovidService): MainViewModel {
        return MainViewModel(service)
    }

    @Provides
    fun provideIndiaViewModel(): IndiaViewModel {
        val retrofit = Retrofit.Builder()
            .baseUrl(COVID_19_INDIA_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        return IndiaViewModel(retrofit)
    }
}