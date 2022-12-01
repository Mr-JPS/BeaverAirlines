package com.example.beaverairlines.adapter

import android.animation.ObjectAnimator
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.beaverairlines.R
import com.example.beaverairlines.data.model.Booking

class FastCheckinAdapter(
    private var datasetBookings: List<Booking>
) : RecyclerView.Adapter<FastCheckinAdapter.ItemViewHolder>() {

    private val lastPosition: Int = -1


    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        val ticketNbr: TextView = view.findViewById(R.id.tv_checkin_RV_bookingNbr)
        val cabinClass: TextView = view.findViewById(R.id.tv_checkin_RV_class)
        val departCity: TextView = view.findViewById(R.id.tv_checkin_RV_departCity)
        val ariCity: TextView = view.findViewById(R.id.tv_checkin_RV_ariCity)
        val departDate: TextView = view.findViewById(R.id.tv_checkin_RV_takeoffDate)
        val passFullname: TextView = view.findViewById(R.id.tv_checkin_RV_passName)
        val checkinNow: TextView = view.findViewById(R.id.tv_checkin_RV_checkinNow)
        val checkinArrow: Button = view.findViewById(R.id.bttn_checkin_RV_arrow)
        val seatAssignment: ConstraintLayout = view.findViewById(R.id.seatAssignment)
        val aisleBox: CheckBox = view.findViewById(R.id.cb_checkin_RV_aisle)
        val middleBox: CheckBox = view.findViewById(R.id.cb_checkin_RV_middle)
        val windowBox: CheckBox = view.findViewById(R.id.cb_checkin_RV_window)
        val loadingBeaver: ImageView = view.findViewById(R.id.iv_checkin_RV_loading)
        val seatFrame: ImageView = view.findViewById(R.id.iv_checkin_RV_seatFrame)
        val seatLetter: TextView = view.findViewById(R.id.tv_checkin_RV_seatLetter)
        val seatNbr: TextView = view.findViewById(R.id.tv_checkin_RV_seatNbr)
        val issueBoardingPass: Button = view.findViewById(R.id.bttn_checkin_RV_issueBoardingpass)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.checkin_main_card_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val booking = datasetBookings[position]


        holder.ticketNbr.text = booking.ticketReservationNbr
        holder.cabinClass.text = booking.flight1_cabinclass
        holder.departCity.text = booking.flight1_departCity
        holder.ariCity.text = booking.flight1_ariCity
        holder.departDate.text = booking.flight1_departDate
        holder.passFullname.text = "${booking.flight1_passFirstname} ${booking.flight1_passSurname}"

        holder.checkinArrow.setOnClickListener {
            if (holder.seatAssignment.visibility == View.GONE) {
                holder.seatAssignment.visibility = View.VISIBLE

                    when {
                        holder.aisleBox.isChecked -> {
                            holder.loadingBeaver.visibility = View.VISIBLE

                                val beaverRotator = ObjectAnimator.ofFloat(
                                    holder.loadingBeaver,
                                    View.ROTATION_Y,
                                    0f, 360f)
                                beaverRotator.duration = 2000
                                beaverRotator.start()


                            holder.loadingBeaver.visibility = View.GONE

                            val aisleLetters = listOf("C", "D")
                            holder.seatLetter.text = aisleLetters.random()

                            val aisleRow = listOf(24..58)
                            holder.seatNbr.text = aisleRow.random().toString()

                            holder.seatFrame.visibility = View.VISIBLE
                            holder.seatLetter.visibility = View.VISIBLE
                            holder.seatNbr.visibility = View.VISIBLE
                            holder.issueBoardingPass.visibility = View.VISIBLE
                        }


                        holder.middleBox.isChecked -> {
                            holder.loadingBeaver.visibility = View.VISIBLE

                            val beaverRotator = ObjectAnimator.ofFloat(
                                holder.loadingBeaver,
                                View.ROTATION_Y,
                                0f, 360f)
                            beaverRotator.duration = 2000
                            beaverRotator.start()


                            holder.loadingBeaver.visibility = View.GONE

                            val middleLetters = listOf("B", "E")
                            holder.seatLetter.text = middleLetters.random()

                            val middleRow = listOf(24..58)
                            holder.seatNbr.text = middleRow.random().toString()

                            holder.seatFrame.visibility = View.VISIBLE
                            holder.seatLetter.visibility = View.VISIBLE
                            holder.seatNbr.visibility = View.VISIBLE
                            holder.issueBoardingPass.visibility = View.VISIBLE
                        }


                        holder.windowBox.isChecked -> {
                            holder.loadingBeaver.visibility = View.VISIBLE

                            val beaverRotator = ObjectAnimator.ofFloat(
                                holder.loadingBeaver,
                                View.ROTATION_Y,
                                0f, 360f)
                            beaverRotator.duration = 2000
                            beaverRotator.start()


                            holder.loadingBeaver.visibility = View.GONE

                            val windowLetters = listOf("A", "F")
                            holder.seatLetter.text = windowLetters.random()

                            val windowRow = listOf(24..58)
                            holder.seatNbr.text = windowRow.random().toString()

                            holder.seatFrame.visibility = View.VISIBLE
                            holder.seatLetter.visibility = View.VISIBLE
                            holder.seatNbr.visibility = View.VISIBLE
                            holder.issueBoardingPass.visibility = View.VISIBLE
                        }
                    }

            } else {
                holder.seatAssignment.visibility = View.GONE
            }
        }

        //SEAT IM INTERFACE SPEICHERN!

    }




    override fun getItemCount(): Int {
        return datasetBookings.size
    }

}