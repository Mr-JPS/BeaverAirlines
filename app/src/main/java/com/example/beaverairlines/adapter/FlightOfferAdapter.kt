package com.example.beaverairlines.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.transition.AutoTransition
import android.transition.Explode
import android.transition.Fade
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.beaverairlines.FlightResultSheetFragment
import com.example.beaverairlines.R
import com.example.beaverairlines.api.Repository
import com.example.beaverairlines.data.FlightOffer
import com.example.beaverairlines.data.model.Airport
import kotlinx.android.synthetic.main.book3_card.*

import java.sql.Date



class FlightOfferAdapter(
    private var datasetFlights: List<FlightOffer>
) : RecyclerView.Adapter<FlightOfferAdapter.ItemViewHolder>() {

//    private var datasetAirports = listOf<Airport>()
    private var lastPosition: Int = -1


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
        val departureIata: TextView = view.findViewById(R.id.tv_departureIATA)
        val arrivalIata: TextView = view.findViewById(R.id.tv_arrivalIATA)
        val flightDuration1: TextView = view.findViewById(R.id.tv_duration)
        val flightDuration2: TextView = view.findViewById(R.id.tv_durationTime)
        val price2: TextView = view.findViewById(R.id.tv_price)
        val flightNbr: TextView = view.findViewById(R.id.tv_flightNbr)
        val innerCard: LinearLayout = view.findViewById(R.id.InnerCard)
        val flightPath: ImageView = view.findViewById(R.id.iv_flightPathIcon)
        val price1: Button = view.findViewById(R.id.bttn_price)
        val whiteLine: ImageView = view.findViewById(R.id.iv_whiteLineFlightResult)
        val outerCard: ConstraintLayout = view.findViewById(R.id.OuterCard)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        // das itemLayout wird gebaut
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.flightresult_item, parent, false)

        // und in einem ViewHolder zurückgegeben
        return ItemViewHolder(adapterLayout)
    }


    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val flights = datasetFlights[position]

//        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//        val formatter = SimpleDateFormat("HH:mm")


        val price = "EUR " + flights.price
        holder.takeOff.text = flights.departureTime.dropLast(3)
        holder.landing.text = flights.arrivalTime.dropLast(3)
        holder.flightDuration1.text = flights.duration.drop(2)
        holder.flightDuration2.text = flights.duration.drop(2)
        //holder.departureIata.text = Repository().offersParsed
        //holder.flightDuration1.text = SimpleDateFormat("HH:mm").format (Date (flights.duration))
        //holder.flightDuration2.text = SimpleDateFormat("mm:ss").format (Date (flights.duration.toLong()))
        holder.price1.text = price
        holder.price2.text = flights.price
        holder.flightNbr.text = flightNbrGenerator()


        //val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)

        holder.innerCard.setOnClickListener {
            if(holder.outerCard.visibility == View.GONE) {
                holder.outerCard.animation = AnimationUtils.loadAnimation(holder.outerCard.context,R.anim.flight_result_anim)
                //TransitionManager.beginDelayedTransition(holder.outerCard, TransitionSet())
                holder.outerCard.visibility = View.VISIBLE
                TransitionManager.beginDelayedTransition(holder.innerCard, AutoTransition())
                holder.flightPath.visibility = View.VISIBLE
                holder.flightDuration1.visibility = View.GONE
                holder.whiteLine.visibility = View.GONE
                holder.price1.visibility = View.GONE

            } else {
                holder.outerCard.animation = AnimationUtils.loadAnimation(holder.outerCard.context,R.anim.zoom_out)
                //TransitionManager.beginDelayedTransition(holder.outerCard, Fade())
                holder.outerCard.visibility = View.GONE
                TransitionManager.beginDelayedTransition(holder.innerCard, AutoTransition())
                holder.flightPath.visibility = View.GONE
                holder.flightDuration1.visibility = View.VISIBLE
                holder.whiteLine.visibility = View.VISIBLE
                holder.price1.visibility = View.VISIBLE

            }

        }


//        val airports: Airport =  datasetAirports[position]
//        holder.departureCity.text = airports.iata_code
//        holder.arrivalCity.text = airports.iata_code
    }

    private fun setAnimation(outerCard: ConstraintLayout, position: Int) {

        if (position > lastPosition){
//            val animation = AnimationUtils.loadAnimation(requireContext(), )
        }
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