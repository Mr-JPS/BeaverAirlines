package com.example.beaverairlines.adapter

import android.annotation.SuppressLint
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.beaverairlines.R
import com.example.beaverairlines.data.FlightOffer
import com.example.beaverairlines.utils.BookInterface
import kotlinx.android.synthetic.main.book3_card.*
import kotlinx.android.synthetic.main.book3_card.view.*
import kotlinx.android.synthetic.main.fragment_book.*
import kotlinx.android.synthetic.main.fragment_flightresultsheet.view.*

// THIS ADAPTER MANAGES THE FUNCTIONALITIES FOR PROVIDING THE PARSED API DATA:

class FlightOfferAdapter(
    private var datasetFlights: List<FlightOffer>,
    private val depIata: String,
    private val ariIata: String,
    private val bookInterface: BookInterface
) : RecyclerView.Adapter<FlightOfferAdapter.ItemViewHolder>() {


    private var lastPosition: Int = -1


    fun submitFlightList(new: List<FlightOffer>) {
        datasetFlights = new
    }



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
        val selectFlight: Button = view.findViewById(R.id.bttn_letsFly)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.flightresult_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }



    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val flights = datasetFlights[position]

        val price = "EUR ${flights.price}"
        holder.takeOff.text = flights.departureTime.dropLast(3)
        holder.landing.text = flights.arrivalTime.dropLast(3)
        holder.flightDuration1.text = flights.duration.drop(2)
        holder.flightDuration2.text = flights.duration.drop(2)
        holder.departureIata.text = depIata
        holder.arrivalIata.text = ariIata
        holder.price1.text = price
        holder.price2.text = flights.price
        holder.flightNbr.text = flightNbrGenerator()


        //ANIMATIONS WHEN ITEM WAS CLICKED:
        holder.innerCard.setOnClickListener {
            if(holder.outerCard.visibility == View.GONE) {
                holder.outerCard.animation = AnimationUtils.loadAnimation(holder.outerCard.context,R.anim.flight_result_anim)
                holder.outerCard.visibility = View.VISIBLE
                TransitionManager.beginDelayedTransition(holder.innerCard, AutoTransition())
                holder.flightPath.visibility = View.VISIBLE
                holder.flightDuration1.visibility = View.GONE
                holder.whiteLine.visibility = View.GONE
                holder.price1.visibility = View.GONE

            } else {
                holder.outerCard.animation = AnimationUtils.loadAnimation(holder.outerCard.context,R.anim.zoom_out)
                holder.outerCard.visibility = View.GONE
                TransitionManager.beginDelayedTransition(holder.innerCard, AutoTransition())
                holder.flightPath.visibility = View.GONE
                holder.flightDuration1.visibility = View.VISIBLE
                holder.whiteLine.visibility = View.VISIBLE
                holder.price1.visibility = View.VISIBLE
            }
        }


        holder.selectFlight.setOnClickListener {

            //TO PASS THE VALUES OVER THE BOOK INTERFACE FOR GENERATING A RETURN FLIGHT:
            bookInterface.openReturnFlight(flights, holder.flightNbr.text.toString())
        }
    }


    //THIS METHOD GENERATES FLIGHT NUMBERS FOR SELECTED FLIGHT:
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