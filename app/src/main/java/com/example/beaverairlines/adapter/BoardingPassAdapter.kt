package com.example.beaverairlines.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.beaverairlines.BookingViewModel
import com.example.beaverairlines.R
import com.example.beaverairlines.data.FinalBoardingPass
import com.example.beaverairlines.utils.BookInterface

class BoardingPassAdapter(
    private var datasetBoardingPass: List<FinalBoardingPass>,
    private val bookingViewModel: BookingViewModel,
    private val bookInterface: BookInterface
) : RecyclerView.Adapter<BoardingPassAdapter.ItemViewHolder>() {

    fun submitBoardingPassList(list: List<FinalBoardingPass>){
        datasetBoardingPass = list
        notifyDataSetChanged()
    }
    private val lastPosition: Int = -1


    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        val bp_boardingTime: TextView = view.findViewById(R.id.BP_boardingTime)
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
        val boardingpass = datasetBoardingPass[position]


        holder.bp_boardingTime.text = boardingpass.boardingtime
        holder.bp_destination.text = boardingpass.destinationIata
        holder.bp_passFullname.text =
            "${boardingpass.passSurname} ${boardingpass.passFirstname}"

        holder.bp_previewCard.setOnClickListener {

            bookingViewModel.loadBp(boardingpass.id)
//            val passFirstname: String = boardingpass.flight1_passFirstname
//            val passSurname: String = boardingpass.flight1_passSurname
//            val destinationIata: String = boardingpass.flight1_ariIATA
//            val boardingtime: String = boardingpass.flight1_takeoffTime
//            val gate: String
//            val assignedSeat: String


        }
    }




    override fun getItemCount(): Int {
        return datasetBoardingPass.size
    }

}

