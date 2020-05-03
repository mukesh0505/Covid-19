package com.mkr.covid19.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StateData(
    val districtData: HashMap<String, DistrictData> = hashMapOf()
): Parcelable {
    fun getActiveCount(): Long {
        var count = 0L
        districtData.keys.forEach {
            count += (districtData[it]?.active ?: 0L)
        }
        return count
    }

    fun getRecoveredCount(): Long {
        var count = 0L
        districtData.keys.forEach {
            count += (districtData[it]?.recovered ?: 0L)
        }
        return count
    }

    fun getDeathCount(): Long {
        var count = 0L
        districtData.keys.forEach {
            count += (districtData[it]?.deceased ?: 0L)
        }
        return count
    }

    fun getConfirmedCount(): Long {
        var count = 0L
        districtData.keys.forEach {
            count += (districtData[it]?.confirmed ?: 0L)
        }
        return count
    }
}