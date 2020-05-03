package com.mkr.covid19

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.mkr.covid19.adapter.CovidListAdapter
import com.mkr.covid19.databinding.ActivityMainBinding
import com.mkr.covid19.model.CovidData
import com.mkr.covid19.repository.CovidService
import com.mkr.covid19.utils.Utils
import com.mkr.covid19.viewModel.MainViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var covidService: CovidService

    @Inject
    lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setUpView()
        bindViewModel()
    }

    private fun setUpView() {
        binding.countryData.apply {
            addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayout.VERTICAL))
        }
        binding.country.setOnClickListener {
            val intent = Intent(this, IndiaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun bindViewModel() {
        binding.apply {
            viewModel.covidData.observe(this@MainActivity, Observer {
                sortData(it)
            })
            viewModel.errorObservable.subscribe {
                binding.apply {
                    showErrorLayout()
                }
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun sortData(covidData: CovidData) {
        viewModel.sortCovidData(covidData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                binding.apply {
                    showCovidLayout()
                    countryData.adapter = CovidListAdapter(it)
                    confirmedCount.text = Utils.getFormattedNumber(it.Global.TotalConfirmed)
                    confirmedDayCount.text = Utils.getFormattedNumber(it.Global.NewConfirmed)
                    recoveredCount.text = Utils.getFormattedNumber(it.Global.TotalRecovered)
                    recoveredDayCount.text = Utils.getFormattedNumber(it.Global.NewRecovered)
                    deceasedCount.text = Utils.getFormattedNumber(it.Global.TotalDeaths)
                    deseasedDayCount.text = Utils.getFormattedNumber(it.Global.NewDeaths)
                    updateUserLocation(it)
                }
            }
    }

    private fun showCovidLayout() {
        binding.apply {
            covidData.visibility = View.VISIBLE
            loader.visibility = View.GONE
            failed.visibility = View.GONE
        }
    }

    private fun showErrorLayout() {
        binding.apply {
            covidData.visibility = View.GONE
            loader.visibility = View.GONE
            failed.visibility = View.VISIBLE
        }
    }

    private fun updateUserLocation(data: CovidData) {
        val countryCode = getUserCountryCode()
        val countryData = data.Countries.first { it.CountryCode.equals(countryCode.first, true) }
        binding.apply {
            countryData.let {
                acrossCountry.text = String.format(getString(R.string.across_country), countryCode.second)
                countryConfirmedCount.text = Utils.getFormattedNumber(it.TotalConfirmed)
                countryConfirmedDayCount.text = Utils.getFormattedNumber(it.NewConfirmed)
                countryRecoveredCount.text = Utils.getFormattedNumber(it.TotalRecovered)
                countryRecoveredDayCount.text = Utils.getFormattedNumber(it.NewRecovered)
                countryDeceasedCount.text = Utils.getFormattedNumber(it.TotalDeaths)
                countryDeseasedDayCount.text = Utils.getFormattedNumber(it.NewDeaths)
            }
        }
    }

    private fun getUserCountryCode(): Pair<String, String> {
        val tm =
            this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return Pair(tm.networkCountryIso, Locale("", tm.networkCountryIso.capitalize()).displayCountry)
    }
}
