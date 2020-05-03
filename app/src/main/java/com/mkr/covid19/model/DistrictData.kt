package com.mkr.covid19.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DistrictData(
    val notes : String,
    val active : Long,
    val confirmed : Long,
    val deceased : Long,
    val recovered : Long
): Parcelable