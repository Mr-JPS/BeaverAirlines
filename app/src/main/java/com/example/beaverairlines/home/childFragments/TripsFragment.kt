package com.example.beaverairlines.home.childFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.beaverairlines.AuthViewModel
import com.example.beaverairlines.BookingViewModel
import com.example.beaverairlines.R
import com.example.beaverairlines.ViewModel
import com.example.beaverairlines.adapter.BookingsAdapter
import com.example.beaverairlines.databinding.FragmentBookBinding
import com.example.beaverairlines.databinding.FragmentTripsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_trips.view.*

class TripsFragment: Fragment() {

    private lateinit var binding: FragmentTripsBinding
    private val flightViewModel: ViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()
    private val bookingViewModel: BookingViewModel by activityViewModels()
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentTripsBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //mh = MILE HIGH
        //rb = RECENT BOOKINGS
        //smh = MILE HIGH STATUS
        val ticketRes = bookingViewModel.reservationNbr

        val mhCard = binding.MHmemberCard
        var mhFirstname = binding.tvMHmemberFirstname
        var mhSurname = binding.tvMHmemberSurname
        var mhClubNbr = binding.tvMHmemberClubnbr

        var mhCardBig = binding.ProfileConstraint5.mileHighPasscard
        var mhBigName = binding.mileHighPasscard.tvMHmemberName
        var mhBigClubNbr = binding.mileHighPasscard.tvMHmemberNbr

        val rbCard = binding.recentBookings
        var rbRecycler = binding.recentBookings.rvRecentFlightRecycler

        var smhFlightsCompleted = binding.tvMHstatusFlightsCompleted
        var smhFlightsDone = binding.tvMHstatusDoneFlights
        var smhMissingPoints = binding.tvMHstatusMissingPoints
        var smhPoints = binding.tvMHstatusPoints


        bookingViewModel.bookingList.observe(
            viewLifecycleOwner,
            Observer {
                rbRecycler.adapter = BookingsAdapter(it)
            }
        )


        db.collection("user").document(auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener {
                val firstname = it.getString("firstName")
                val surname = it.getString("lastName")
                val clubNbr = it.getString("mileHighClubNbr")

                mhFirstname.text = firstname
                mhSurname.text = surname
                mhClubNbr.text = clubNbr

                mhBigName.text = "${firstname} ${surname}"
                mhBigClubNbr.text = "MILE HIGH CLUB ${clubNbr}"
            }

        mhCard.setOnClickListener {
            mhCardBig.visibility = View.VISIBLE

            mhCardBig.setOnClickListener {
                mhCardBig.visibility = View.GONE
            }
        }



//        bookingViewModel.getBooking(ticketRes)
//
//        bookingViewModel.currentBooking.observe(
//            viewLifecycleOwner,
//            Observer {
//                it.flight1_ariCity
//
//            }
//        )




    }

    private fun getUser() {

    }

}