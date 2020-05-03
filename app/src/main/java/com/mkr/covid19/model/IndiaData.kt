package com.mkr.covid19.model

data class IndiaData(
    val data: HashMap<String, StateData> = hashMapOf()
)