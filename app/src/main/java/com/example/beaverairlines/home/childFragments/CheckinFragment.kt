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
import com.example.beaverairlines.adapter.FastCheckinAdapter
import com.example.beaverairlines.data.FlightOffer
import com.example.beaverairlines.databinding.FragmentBookBinding
import com.example.beaverairlines.databinding.FragmentCheckinBinding
import com.example.beaverairlines.utils.BookInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_checkin.view.*


class CheckinFragment : Fragment(), BookInterface {

    private lateinit var binding : FragmentCheckinBinding
    private val flightViewModel: ViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()
    private val bookingViewModel: BookingViewModel by activityViewModels()
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentCheckinBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val client: DuffelApiClient = ("duffel_test_VBLYDE4AS2Cg7SVBdEYFvrahFvhFqQo1HTygIv7FDje")

        val bigPlane = binding.ivCheckinBigPlaneBG
        val seats = binding.ivCheckinSeats
        val cloud1 = binding.ivCheckinCloud1
        val cloud2 = binding.ivCheckinCloud2

        val fastCheckinCard = binding.checkinMain
        val fastCheckinRecycler = binding.checkinMain.rvRecentFlightsRecycler





        val allBoardingPassesCard = binding.allBoardingpasses
        val allBoardingPassesRecycler = binding.allBoardingpasses.BPRecycler





        val finalBoardingPassLayout = binding.finalBoardingPassLayout
        val finalBP = binding.finalBoardingPass
        val finalBPgate = binding.finalBoardingPass.boardingcardGateNbr
        val finalBPpassFirstname = binding.finalBoardingPass.boardingcardPasFirstname
        val finalBPpassSurname = binding.finalBoardingPass.boardingcardPasSurname
        val finalBPseatNbr = binding.finalBoardingPass.boardingcardSeatNbr
        val finalBPdestination = binding.finalBoardingPass.boardingcardDestination
        val finalBPboardingTime = binding.finalBoardingPass.boardingcardBoardingTime


        bookingViewModel.bookingList.observe(
            viewLifecycleOwner,
            Observer {
                fastCheckinRecycler.adapter = FastCheckinAdapter(it,  bookingViewModel, this)
            }
        )

        bookingViewModel.isBoardingPassIssued.observe(
            viewLifecycleOwner,
            Observer {
                if (it){
                    val boardingPass = bookingViewModel.finalBoardingPass
                    finalBPgate.text = boardingPass.value?.gate
                    finalBPpassFirstname.text = boardingPass.value?.passFirstname
                    finalBPpassSurname.text = boardingPass.value?.passSurname
                    finalBPseatNbr.text = boardingPass.value?.assignedSeat
                    finalBPdestination.text = boardingPass.value?.destinationIata
                    finalBPboardingTime.text = boardingPass.value?.boardingtime

                    finalBoardingPassLayout.visibility = View.VISIBLE
                }
            }
        )

//        bookingViewModel.isBoardingPassIssued.
//        if (bookingViewModel.isBoardingPassIssued){
//            finalBPpassFirstname.text = bookingViewModel.passFirstname
//            finalBPpassSurname.text = bookingViewModel.passSurname
//            finalBPdestination.text = bookingViewModel.destinationIata
//            finalBPboardingTime.text = bookingViewModel.boardingtime
//            finalBPgate.text = bookingViewModel.gate
//            finalBPseatNbr.text = bookingViewModel.assignedSeat
//        }



    }

    override fun openFlight() {
        TODO("Not yet implemented")
    }

    override fun openReturnFlight(flight: FlightOffer, bookingNbr: String) {
        TODO("Not yet implemented")
    }

    override fun openPayment(
        depIata: String,
        ariIata: String,
        returnFlight: FlightOffer,
        returnBookingNbr: String,
    ) {
        TODO("Not yet implemented")
    }



}