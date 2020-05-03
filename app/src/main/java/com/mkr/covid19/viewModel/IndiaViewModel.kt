package com.mkr.covid19.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.mkr.covid19.model.DistrictData
import com.mkr.covid19.model.IndiaData
import com.mkr.covid19.model.StateData
import com.mkr.covid19.repository.Covid19IndiaService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Exception
import java.util.concurrent.TimeUnit

class IndiaViewModel(private val retrofit: Retrofit) : ViewModel() {

    val errorObservable = PublishSubject.create<Unit>()
    val indiaData = MutableLiveData<IndiaData>()


    init {
        getIndiaData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    private fun getIndiaData() = Observable.interval(0, 15, TimeUnit.MINUTES)
        .map {
            retrofit
                .create(Covid19IndiaService::class.java)
                .fetchIndiaData()
                .enqueue(object : Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        errorObservable.onNext(Unit)
                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        response.body()?.let {
                            val jsonObject = JSONObject(it)
                            createIndiaStatistic(jsonObject)?.let {
                                indiaData.postValue(it)
                            } ?: kotlin.run {
                                errorObservable.onNext(Unit)
                            }
                        } ?: kotlin.run {
                            errorObservable.onNext(Unit)
                        }
                    }
                })
        }

    private fun createIndiaStatistic(jsonObject: JSONObject): IndiaData? {
        try {
            val indiaData = IndiaData()
            jsonObject.keys().forEach { state ->
                val indianState = StateData()
                val stateData = jsonObject.getString(state)
                val stateObject = JSONObject(stateData)
                val districtData = stateObject.getString("districtData")
                val districtObject = JSONObject(districtData)
                districtObject.keys().forEach {
                    val district = districtObject.getString(it)
                    val disObject = Gson().fromJson(district, DistrictData::class.java)
                    indianState.districtData[it] = disObject
                }
                indiaData.data[state] = indianState
            }
            return indiaData
        } catch (ignored: Exception) {
            return null
        }
    }
}