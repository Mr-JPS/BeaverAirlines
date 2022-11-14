package com.example.beaverairlines.adapter

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.beaverairlines.R
import com.example.beaverairlines.data.FlightOffer
import com.example.beaverairlines.data.model.Airport
import java.sql.Date


class FlightOfferAdapter(
    private var datasetFlights: List<FlightOffer>
) : RecyclerView.Adapter<FlightOfferAdapter.ItemViewHolder>() {

//    private var datasetAirports = listOf<Airport>()

    fun submitFlightList(new: List<FlightOffer>) {
        datasetFlights = new
    }

//    fun submitAirportList(list: List<Airport>){
//        datasetAirports = list
//        notifyDataSetChanged()
//    }

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val takeOff: TextView = view.findViewById(R.id.tv_takeoffTime)
        val landing: TextView = view.findViewById(R.id.tv_landingTime)
        val departureCity: TextView = view.findViewById(R.id.tv_departureIATA)
        val arrivalCity: TextView = view.findViewById(R.id.tv_arrivalIATA)
        val flightDuration1: TextView = view.findViewById(R.id.tv_duration)
        val flightDuration2: TextView = view.findViewById(R.id.tv_durationTime)
        val price1: TextView = view.findViewById(R.id.bttn_price)
        val price2: TextView = view.findViewById(R.id.tv_price)
        val flightNbr: TextView = view.findViewById(R.id.tv_flightNbr)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        // das itemLayout wird gebaut
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.flightresult_item, parent, false)

        // und in einem ViewHolder zurückgegeben
        return ItemViewHolder(adapterLayout)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val flights = datasetFlights[position]

        holder.takeOff.text = flights.departureTime
        holder.landing.text = flights.arrivalTime
        holder.flightDuration1.text = SimpleDateFormat("mm:ss").format (Date (flights.duration.toLong()))
        holder.flightDuration2.text = SimpleDateFormat("mm:ss").format (Date (flights.duration.toLong()))
        holder.price1.text = flights.price
        holder.price2.text = flights.price
        holder.flightNbr.text = flightNbrGenerator()


//        val airports: Airport =  datasetAirports[position]
//        holder.departureCity.text = airports.iata_code
//        holder.arrivalCity.text = airports.iata_code
    }

    private fun flightNbrGenerator(): String {

        val nbr1 = (0..9).random()
        val nbr2 = (0..9).random()
        val nbr3 = (0..9).random()
        val nbr4 = (0..9).random()

        return "BA-$nbr1$nbr2$nbr3$nbr4"
    }

    override fun getItemCount(): Int {
        return datasetFlights.size
    }
}