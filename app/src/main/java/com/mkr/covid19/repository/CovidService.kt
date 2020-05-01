package com.mkr.covid19.repository

import com.mkr.covid19.model.CovidData
import retrofit2.Call
import retrofit2.http.GET

interface CovidService {

    @GET("/summary")
    fun getWorldData(): Call<CovidData>
}