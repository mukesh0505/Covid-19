package com.mkr.covid19.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkr.covid19.databinding.ItemCovidStateBinding
import com.mkr.covid19.model.IndiaData
import com.mkr.covid19.model.StateData
import com.mkr.covid19.utils.Utils

class CovidStateListAdapter(
    covidData: IndiaData,
    val listener: StateClickListener
) : RecyclerView.Adapter<CovidStateListAdapter.CovidState>() {

    private val stateData = covidData.data
    private val states = ArrayList(stateData.keys.sorted())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CovidState {
        val itemBinding =
            ItemCovidStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CovidState(itemBinding)
    }

    override fun getItemCount(): Int = stateData.size

    override fun onBindViewHolder(holder: CovidState, position: Int) {
        states[position]?.let { state ->
            stateData[state]?.let {
                holder.bind(state.toString(), it)
            }
        }
    }

    inner class CovidState(private val binding: ItemCovidStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(stateName: String, data: StateData) {
            binding.apply {
                data.let {
                    state.text = stateName
                    confirmedCount.text = Utils.getFormattedNumber(it.getConfirmedCount())
                    recoveredCount.text = Utils.getFormattedNumber(it.getRecoveredCount())
                    deceasedCount.text = Utils.getFormattedNumber(it.getDeathCount())
                }
                root.setOnClickListener {
                    listener.onStateClicked(stateName, data)
                }
            }
        }
    }
}

interface StateClickListener {
    fun onStateClicked(stateName: String, stateData: StateData)
}