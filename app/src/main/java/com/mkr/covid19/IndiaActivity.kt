package com.mkr.covid19

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.mkr.covid19.adapter.CovidStateListAdapter
import com.mkr.covid19.adapter.StateClickListener
import com.mkr.covid19.databinding.ActivityIndiaBinding
import com.mkr.covid19.model.StateData
import com.mkr.covid19.viewModel.IndiaViewModel
import javax.inject.Inject

class IndiaActivity : AppCompatActivity(), StateClickListener {

    private lateinit var binding: ActivityIndiaBinding

    @Inject
    lateinit var viewModel: IndiaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_india)
        setUpView()
        bindViewModel()
    }

    private fun setUpView() {
        binding.stateList.apply {
            addItemDecoration(DividerItemDecoration(this@IndiaActivity, LinearLayout.VERTICAL))
        }
    }

    @SuppressLint("CheckResult")
    private fun bindViewModel() {
        viewModel.indiaData.observe(this, Observer {
            binding.apply {
                loader.visibility = View.GONE
                header.visibility = View.VISIBLE
                failed.visibility = View.GONE
                stateList.adapter = CovidStateListAdapter(it, this@IndiaActivity)
            }
        })
        viewModel.errorObservable.subscribe {
            binding.apply {
                loader.visibility = View.GONE
                header.visibility = View.GONE
                failed.visibility = View.VISIBLE
                stateList.visibility = View.GONE
            }
        }
    }

    override fun onStateClicked(stateName: String, stateData: StateData) {
        val intent = Intent(this, StateActivity::class.java)
        intent.putExtra(StateActivity.STATE_NAME, stateName)
        intent.putExtra(StateActivity.STATE_DATA, stateData)
        startActivity(intent)
    }
}
