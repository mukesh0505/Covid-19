package com.mkr.covid19

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.mkr.covid19.adapter.CovidListAdapter
import com.mkr.covid19.databinding.ActivityMainBinding
import com.mkr.covid19.repository.CovidService
import com.mkr.covid19.viewModel.MainViewModel
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
    }

    private fun bindViewModel() {
        binding.apply {
            viewModel.covidData.observe(this@MainActivity, Observer {
                showCovidLayout()
                countryData.adapter = CovidListAdapter(it)
                confirmedCount.text = it.Global.TotalConfirmed.toString()
                confirmedDayCount.text = it.Global.NewConfirmed.toString()
                recoveredCount.text = it.Global.TotalRecovered.toString()
                recoveredDayCount.text = it.Global.NewRecovered.toString()
                deceasedCount.text = it.Global.TotalDeaths.toString()
                deseasedDayCount.text = it.Global.NewDeaths.toString()
            })
            viewModel.errorObservable.subscribe {
                binding.apply {
                    showErrorLayout()
                }
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
}
