package com.example.beaverairlines.adapter

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.beaverairlines.BookingViewModel
import com.example.beaverairlines.R
import com.example.beaverairlines.data.FinalBoardingPass
import com.example.beaverairlines.data.model.Booking
import com.example.beaverairlines.utils.BookInterface

class FastCheckinAdapter(
    private var datasetBookings: List<Booking>,
    private val bookingViewModel: BookingViewModel,
    private val bookInterface: BookInterface
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

    @SuppressLint("SuspiciousIndentation")
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

//                            var assignedSeat = "${holder.seatLetter}${holder.seatNbr}"
//                            bookInterface.saveSeatAssignment(assignedSeat)
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

//                            var assignedSeat = "${holder.seatLetter}${holder.seatNbr}"
//                            bookInterface.saveSeatAssignment(assignedSeat)
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

//                            var assignedSeat = "${holder.seatLetter}${holder.seatNbr}"
//                            bookInterface.saveSeatAssignment(assignedSeat)
                        }
                    }

            } else {
                holder.seatAssignment.visibility = View.GONE
            }


            //GGF FÜR DEN RÜCKFLUG AUCH NOCH EINEN ADAPTER MACHEN


            holder.issueBoardingPass.setOnClickListener {
                val passFirstName = booking.flight1_passFirstname
                val passSurname = booking.flight1_passSurname
                val destinationIata = booking.flight1_ariIATA
                val boardingtime = booking.flight1_takeoffTime
                val gate = gateGenerator()
                val assignedSeat = "${holder.seatLetter}${holder.seatNbr}"

                val newBP = FinalBoardingPass(passFirstName,passSurname,destinationIata,boardingtime, gate, assignedSeat)
                bookingViewModel.saveIssuedBoardinPass(newBP)


//                bookingViewModel.isBoardingPassIssued = true
//                bookingViewModel.passFirstname = passFirstName
//                bookingViewModel.passSurname = passSurname
//                bookingViewModel.destinationIata = destinationIata
//                bookingViewModel.boardingtime = boardingtime
//                bookingViewModel.gate = gate
//                bookingViewModel.assignedSeat = assignedSeat
            }
        }
    }

    private fun gateGenerator(): String {

        val gateLetter = listOf("A", "B", "C", "D", "E", "F", "G")
        val gateNbr = listOf(1..33)

        val generatedGate = "${gateLetter.random()}${gateNbr.random()}"

        return generatedGate
    }


    override fun getItemCount(): Int {
        return datasetBookings.size
    }

}