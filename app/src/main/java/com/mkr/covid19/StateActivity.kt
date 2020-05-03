package com.mkr.covid19

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.mkr.covid19.adapter.CovidDistrictListAdapter
import com.mkr.covid19.databinding.ActivityStateBinding
import com.mkr.covid19.model.StateData

class StateActivity : AppCompatActivity() {

    companion object {
        const val STATE_NAME = "state_name"
        const val STATE_DATA = "state_data"
    }

    private lateinit var binding: ActivityStateBinding

    private lateinit var stateName: String
    private lateinit var stateData: StateData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_state)
        getDataFromIntent()
        setUpView()
    }

    private fun getDataFromIntent() {
        stateName = intent.getStringExtra(STATE_NAME)
        stateData = intent.getParcelableExtra(STATE_DATA)
    }

    private fun setUpView() {
        binding.stateList.apply {
            addItemDecoration(DividerItemDecoration(this@StateActivity, LinearLayout.VERTICAL))
            adapter = CovidDistrictListAdapter(stateData)
        }
    }
}
