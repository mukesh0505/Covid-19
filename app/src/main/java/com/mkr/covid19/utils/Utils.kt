package com.mkr.covid19.utils

object Utils {

    fun getCountryImageUrl(countryCode: String): String {
        return String.format(COUNTRY_IMAGE_URL, countryCode)
    }
}