package com.mkr.covid19.repository

import retrofit2.Call
import retrofit2.http.GET

interface Covid19IndiaService {

    @GET("/state_district_wise.json")
    fun fetchIndiaData(): Call<String>
}