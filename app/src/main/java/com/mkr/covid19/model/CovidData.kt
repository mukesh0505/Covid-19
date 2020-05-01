package com.mkr.covid19.model

data class CovidData(
    val Global: GlobalData,
    val Countries: List<CountryData>
)