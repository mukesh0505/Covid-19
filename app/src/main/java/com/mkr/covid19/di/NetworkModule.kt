package com.mkr.covid19.di

import com.mkr.covid19.repository.CovidService
import com.mkr.covid19.utils.BASE_URL
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideCovidService(retrofit: Retrofit): CovidService {
        return retrofit.create(CovidService::class.java)
    }
}