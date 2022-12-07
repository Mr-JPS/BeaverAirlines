package com.example.beaverairlines.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beaverairlines.R
import com.example.beaverairlines.data.model.Airport

// THIS ADAPTER MANAGES THE FUNCTIONALITIES FOR PROVIDING THE RIGHT IATA-CODES FOR MATCHING AIRPORTS

class AirportAdapter() : RecyclerView.Adapter<AirportAdapter.ItemViewHolder>() {

    private var datasetAirports = listOf<Airport>()

    fun submitAirportList(list: List<Airport>){
        datasetAirports = list
        notifyDataSetChanged()
    }



    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val departureCity: TextView = view.findViewById(R.id.tv_departCitySelect)
        //val departureIata: TextView = view.findViewById(R.id.tv_IATAdeparture)
        val arriveCity: TextView = view.findViewById(R.id.tv_arriveCitySelect)
        //val arriveIata: TextView = view.findViewById(R.id.tv_IATAarrival)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        // das itemLayout wird gebaut
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.book3_card, parent, false)

        // und in einem ViewHolder zurückgegeben
        return ItemViewHolder(adapterLayout)
    }



    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val airports: Airport =  datasetAirports[position]

        holder.departureCity.text = airports.airport_name
        //holder.departureIata.text = airports.iata_code
        holder.arriveCity.text = airports.airport_name
        //holder.arriveIata.text = airports.iata_code
    }



    override fun getItemCount(): Int {
        return datasetAirports.size
    }
}