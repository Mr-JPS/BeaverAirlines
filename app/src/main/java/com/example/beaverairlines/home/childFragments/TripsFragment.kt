package com.example.beaverairlines.home.childFragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.beaverairlines.AuthViewModel
import com.example.beaverairlines.BookingViewModel
import com.example.beaverairlines.R
import com.example.beaverairlines.ViewModel
import com.example.beaverairlines.adapter.BookingsAdapter
import com.example.beaverairlines.databinding.FragmentTripsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_trips.view.*

//FRAGMENT HANDLING TRIP DEDICATED ACTIVITIES:

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



    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fadeOUT = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.fade_out_superfast)

        val fadeIN = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.fade_in_superfast)

        //VALUES FOR PASSPORT DISPLAY
        val ticketRes = bookingViewModel.reservationNbr
        val showPassport = binding.tvMHmemberPassport
        val passPCard = binding.userSavedPassport.passportView
        val passPCountry = binding.userSavedPassport.tvPasCountry
        val passPNbr = binding.userSavedPassport.tvPasPassportNbr1
        val passPSurname = binding.userSavedPassport.tvPasSurname
        val passPFirstname = binding.userSavedPassport.tvPasName
        val passPNationality = binding.userSavedPassport.tvPasNationality
        val passPBday = binding.userSavedPassport.tvPasBirthday
        val passPBcity = binding.userSavedPassport.tvPasBirthCity
        val passPGender = binding.userSavedPassport.tvPasGender
        val passPNbr2 = binding.userSavedPassport.tvPasPassportNbr2
        val passPFooter = binding.userSavedPassport.tvPasPassportFoot
        val passPCloseBttn = binding.userSavedPassport.PassBttnClose


        //VALUES FOR MEMBER CARD DISPLAY
        val mhCard = binding.MHmemberCard
        var mhFirstname = binding.tvMHmemberFirstname
        var mhSurname = binding.tvMHmemberSurname
        var mhClubNbr = binding.tvMHmemberClubnbr

        var mhCardBig = binding.ProfileConstraint5.mileHighPasscard
        var mhBigName = binding.mileHighPasscard.tvMHmemberName
        var mhBigClubNbr = binding.mileHighPasscard.tvMHmemberNbr

        val rbCard = binding.recentBookings
        var rbRecycler = binding.recentBookings.rvRecentFlightRecycler
        val rBNoBookingAvail = binding.recentBookings.tvRecentFlightsNoBookings

        var smhFlightsCompleted = binding.tvMHstatusFlightsCompleted
        var smhFlightsDone = binding.tvMHstatusDoneFlights
        var smhMissingPoints = binding.tvMHstatusMissingPoints
        var smhPoints = binding.tvMHstatusPoints


        //METHOD FOR PASSPORT DISPLAY
        showPassport.setOnClickListener {

            db.collection("user").document(auth.currentUser!!.uid)
                .get()
                .addOnSuccessListener {

                    passPCountry.text = it.getString("country")
                    passPNbr.text = it.getString("passportNbr")
                    passPSurname.text = it.getString("lastName")
                    passPFirstname.text =  it.getString("firstName")
                    passPNationality.text = it.getString("nationality")
                    passPBday.text = it.getString("birthDate")
                    passPBcity.text = it.getString("birthPlace")
                    passPGender.text = it.getString("gender")
                    passPNbr2.text = it.getString("passportNbr")

                    passPFooter.text = "***${passPNbr.text.toString()}***${passPSurname.text.toString()}***${passPFirstname.text.toString()}***" +
                            "***********************************************************************"

                    passPCard.visibility = View.VISIBLE
                    passPCard.startAnimation(fadeIN)
                }

            passPCloseBttn.setOnClickListener {
                passPCard.startAnimation(fadeOUT)
                passPCard.visibility = View.GONE
            }
        }


        //OBSERVER TO WATCH CHANGES MADE IN VIEWMODELS BOOKING LIST:
        bookingViewModel.bookingList.observe(
            viewLifecycleOwner,
            Observer {
                rbRecycler.adapter = BookingsAdapter(it)
                rBNoBookingAvail.visibility = View.GONE
                rbRecycler.visibility = View.VISIBLE
            }
        )



        //CODE FOR RETRIEVING DATA FROM FIREBASE:
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
                mhBigClubNbr.text = "${clubNbr}"
            }

            mhCard.setOnClickListener {
            mhCardBig.visibility = View.VISIBLE
            mhCardBig.startAnimation(fadeIN)

            mhCardBig.setOnClickListener {
            mhCardBig.startAnimation(fadeOUT)
            mhCardBig.visibility = View.GONE
            }
        }
    }


    //UNUSED CODE
    private fun getUser() {
    }

}