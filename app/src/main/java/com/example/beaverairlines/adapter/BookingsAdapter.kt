package com.example.beaverairlines.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beaverairlines.R
import com.example.beaverairlines.data.model.Booking

class BookingsAdapter(
    private var datasetBookings: List<Booking>
    //,private val reservationNbr: String

) : RecyclerView.Adapter<BookingsAdapter.ItemViewHolder>(){

    private val lastPosition: Int = -1

    fun submitBookingList(new: List<Booking>){
        datasetBookings = new
    }


    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        val bookingNbr: TextView = view.findViewById(R.id.tv_recentBookingItem_bookingNbr)
        val cabinClass: TextView = view.findViewById(R.id.tv_recentBookingItem_class)
        val departCity: TextView = view.findViewById(R.id.tv_recentBookingItem_departCity)
        val ariCity: TextView = view.findViewById(R.id.tv_recentBookingItem_ariCity)
        val departDate: TextView = view.findViewById(R.id.tv_recentBookingItem_takeoffDate)
        val returnDate: TextView = view.findViewById(R.id.tv_recentBookingItem_returnDate)
        val passFullname: TextView = view.findViewById(R.id.tv_recentBookingItem_passName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.recent_bookings_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val booking = datasetBookings[position]

        holder.bookingNbr.text = booking.ticketReservationNbr
        holder.cabinClass.text = booking.flight1_cabinclass
        holder.departCity.text = booking.flight1_departCity
        holder.ariCity.text = booking.flight1_ariCity
        holder.departDate.text = booking.flight1_departDate
        holder.returnDate.text = booking.flight2_departDate
        holder.passFullname.text = "${booking.flight1_passFirstname} ${booking.flight1_passSurname}"
    }

    override fun getItemCount(): Int {
      return datasetBookings.size
    }


}