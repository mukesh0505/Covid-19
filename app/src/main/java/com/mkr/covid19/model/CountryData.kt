package com.mkr.covid19.model

data class CountryData(
    val Country: String,
    val CountryCode: String,
    val Slug: String,
    val NewConfirmed: Long,
    val TotalConfirmed: Long,
    val NewDeaths: Long,
    val TotalDeaths: Long,
    val NewRecovered: Long,
    val TotalRecovered: Long,
    val Date: String
)