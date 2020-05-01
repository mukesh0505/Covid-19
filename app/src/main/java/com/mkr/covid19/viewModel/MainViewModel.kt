package com.mkr.covid19.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mkr.covid19.model.CovidData
import com.mkr.covid19.repository.CovidService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class MainViewModel(
    private val service: CovidService
) : ViewModel() {

    val covidData = MutableLiveData<CovidData>()

    val errorObservable: Observable<Unit>
        get() = error.hide()

    private val error = BehaviorSubject.create<Unit>()

    init {
        fetchCovidData()
    }

    private fun fetchCovidData() {
        Observable.interval(0, 15, TimeUnit.MINUTES)
            .map {
                service.getWorldData().enqueue(object : Callback<CovidData> {
                    override fun onFailure(call: Call<CovidData>, t: Throwable) {
                        error.onNext(Unit)
                    }

                    override fun onResponse(call: Call<CovidData>, response: Response<CovidData>) {
                        response.body()?.let {
                            covidData.postValue(it)
                        }
                    }
                })
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun sortCovidData(covidData: CovidData): Observable<CovidData> = Observable.create {
        val sorted = covidData.Countries.sortedBy {
            it.TotalConfirmed
        }.reversed()
        it.onNext(CovidData(covidData.Global, sorted))
    }
}