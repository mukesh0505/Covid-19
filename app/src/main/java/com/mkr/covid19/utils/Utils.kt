package com.mkr.covid19.utils

import java.text.DecimalFormat

object Utils {

    fun getCountryImageUrl(countryCode: String): String {
        return String.format(COUNTRY_IMAGE_URL, countryCode)
    }

    fun getFormattedNumber(number: Long): String {
        val pattern = "###,###.###"
        val decimalFormat = DecimalFormat(pattern)
        return decimalFormat.format(number)
    }

}
