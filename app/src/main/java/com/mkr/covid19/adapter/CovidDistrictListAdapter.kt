package com.mkr.covid19.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkr.covid19.databinding.ItemCovidDistrictBinding
import com.mkr.covid19.databinding.ItemCovidStateBinding
import com.mkr.covid19.model.DistrictData
import com.mkr.covid19.model.StateData
import com.mkr.covid19.utils.Utils

class CovidDistrictListAdapter(
    covidData: StateData
) : RecyclerView.Adapter<CovidDistrictListAdapter.CovidDistrict>() {

    private val stateData = covidData.districtData
    private val states = ArrayList(stateData.keys.sorted())


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CovidDistrict {
        val itemBinding =
            ItemCovidDistrictBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CovidDistrict(itemBinding)
    }

    override fun getItemCount(): Int = stateData.size

    override fun onBindViewHolder(holder: CovidDistrict, position: Int) {
        states[position]?.let { state ->
            stateData[state]?.let {
                holder.bind(state, it)
            }
        }
    }

    inner class CovidDistrict(private val binding: ItemCovidDistrictBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(stateName: String, data: DistrictData) {
            binding.apply {
                data.let {
                    state.text = stateName
                    confirmedCount.text = Utils.getFormattedNumber(it.confirmed)
                    recoveredCount.text = Utils.getFormattedNumber(it.recovered)
                    deceasedCount.text = Utils.getFormattedNumber(it.deceased)
                }
            }
        }
    }
}