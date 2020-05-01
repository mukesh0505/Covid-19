package com.mkr.covid19.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkr.covid19.databinding.ItemCovidCountryBinding
import com.mkr.covid19.model.CountryData
import com.mkr.covid19.model.CovidData

class CovidListAdapter(
    covidData: CovidData
) : RecyclerView.Adapter<CovidListAdapter.CovidItem>() {

    private val countryData = covidData.Countries.sortedBy {
        it.TotalConfirmed
    }.reversed()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CovidItem {
        val itemBinding =
            ItemCovidCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CovidItem(itemBinding)
    }

    override fun getItemCount(): Int = countryData.size

    override fun onBindViewHolder(holder: CovidItem, position: Int) =
        holder.bind(countryData[position])

    inner class CovidItem(private val binding: ItemCovidCountryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CountryData) {
            binding.apply {
                data.let {
                    country.text = it.Country
                    confirmedCount.text = it.TotalConfirmed.toString()
                    confirmedDayCount.text = it.NewConfirmed.toString()
                    recoveredCount.text = it.TotalRecovered.toString()
                    recoveredDayCount.text = it.NewRecovered.toString()
                    deceasedCount.text = it.TotalDeaths.toString()
                    deseasedDayCount.text = it.NewDeaths.toString()
                }
            }
        }
    }
}