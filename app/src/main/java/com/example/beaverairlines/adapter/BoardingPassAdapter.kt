package com.example.beaverairlines.adapter

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.beaverairlines.R
import com.example.beaverairlines.data.model.Booking
import com.example.beaverairlines.utils.BookInterface

class BoardingPassAdapter(
    private var datasetBookings: List<Booking>,
    private val bookInterface: BookInterface
) : RecyclerView.Adapter<BoardingPassAdapter.ItemViewHolder>() {

    private val lastPosition: Int = -1


    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        val bp_flightNbr: TextView = view.findViewById(R.id.BP_flightNbr)
        val bp_passFullname: TextView = view.findViewById(R.id.BP_pasName)
        val bp_destination: TextView= view.findViewById(R.id.BP_destination)
        val bp_previewCard: CardView = view.findViewById(R.id.BP_boardingPassPreviewCard)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.booking_pass_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val booking = datasetBookings[position]


        holder.bp_flightNbr.text = booking.flight1_flightNbr
        holder.bp_destination.text = booking.flight1_ariIATA
        holder.bp_passFullname.text =
            "${booking.flight1_passFirstname} ${booking.flight1_passSurname}"

        holder.bp_previewCard.setOnClickListener {

        }
    }




    override fun getItemCount(): Int {
        return datasetBookings.size
    }

}