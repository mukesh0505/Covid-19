package com.mkr.covid19.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkr.covid19.databinding.ItemCovidCountryBinding
import com.mkr.covid19.model.CountryData
import com.mkr.covid19.model.CovidData
import com.mkr.covid19.utils.Utils

class CovidListAdapter(
    private val covidData: CovidData
) : RecyclerView.Adapter<CovidListAdapter.CovidItem>() {

    private val countryData = covidData.Countries

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
                    confirmedCount.text = Utils.getFormattedNumber(it.TotalConfirmed)
                    confirmedDayCount.text = Utils.getFormattedNumber(it.NewConfirmed)
                    recoveredCount.text = Utils.getFormattedNumber(it.TotalRecovered)
                    recoveredDayCount.text = Utils.getFormattedNumber(it.NewRecovered)
                    deceasedCount.text = Utils.getFormattedNumber(it.TotalDeaths)
                    deseasedDayCount.text = Utils.getFormattedNumber(it.NewDeaths)
                }
            }
        }
    }
}