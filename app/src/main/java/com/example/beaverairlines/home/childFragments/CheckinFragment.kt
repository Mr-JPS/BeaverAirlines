package com.example.beaverairlines.home.childFragments

import android.Manifest
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.beaverairlines.AuthViewModel
import com.example.beaverairlines.BookingViewModel
import com.example.beaverairlines.R
import com.example.beaverairlines.ViewModel
import com.example.beaverairlines.adapter.BoardingPassAdapter
import com.example.beaverairlines.data.FinalBoardingPass
import com.example.beaverairlines.data.FlightOffer
import com.example.beaverairlines.databinding.FragmentCheckinBinding
import com.example.beaverairlines.utils.BookInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.boarding_pass_recycler.view.*
import kotlinx.android.synthetic.main.fragment_checkin.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


//FRAGMENT HANDLING THE COMPLETE CHECKIN PROCEDURE:

class CheckinFragment : Fragment(), BookInterface {

    private lateinit var binding: FragmentCheckinBinding
    private val flightViewModel: ViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()
    private val bookingViewModel: BookingViewModel by activityViewModels()
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private var checkIn1: Boolean = false
    private var checkIn2: Boolean = false



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentCheckinBinding.inflate(inflater)
        return binding.root
    }



    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //ANIMATION UTILS:
        val rotate = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.rotate)
            rotate.duration = 2000

        val fadeOUT = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.fade_out_superfast)

        val fadeIN = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.fade_in_fast)



        //NECESSARY VALUES:
        val bigPlane = binding.ivCheckinBigPlaneBG
        val seats = binding.ivCheckinSeats
        val cloud1 = binding.ivCheckinCloud1
        val cloud2 = binding.ivCheckinCloud2
        val cICard = binding.checkinMain.completeBoardingCard
        val cINoAvl = binding.checkinMain.tvFastCheckinHeader2
        val reservationNbr = bookingViewModel.reservationNbr

        val cIFlight1 = binding.checkinMain.readyForCIFlight1
        val cIFlight1_bg = binding.checkinMain.ivRecentFlightsFlight1.checkinRVConstraint
        val cIFlight1_ticketNbr = binding.checkinMain.ivRecentFlightsFlight1.tvCheckinRVBookingNbr
        val cIFlight1_cabinClass = binding.checkinMain.ivRecentFlightsFlight1.tvCheckinRVClass
        val cIFlight1_departCity = binding.checkinMain.ivRecentFlightsFlight1.tvCheckinRVDepartCity
        val cIFlight1_ariCity = binding.checkinMain.ivRecentFlightsFlight1.tvCheckinRVAriCity
        val cIFlight1_departDate = binding.checkinMain.ivRecentFlightsFlight1.tvCheckinRVTakeoffDate
        val cIFlight1_passFullname = binding.checkinMain.ivRecentFlightsFlight1.tvCheckinRVPassName
        val cIFlight1_checkinNow = binding.checkinMain.ivRecentFlightsFlight1.tvCheckinRVCheckinNow
        val cIFlight1_checkinArrow = binding.checkinMain.ivRecentFlightsFlight1.bttnCheckinRVArrow
        val cIFlight1_seatAssignment = binding.checkinMain.ivRecentFlightsFlight1.seatAssignment
        val cIFlight1_aisleBox = binding.checkinMain.ivRecentFlightsFlight1.cbCheckinRVAisle
        val cIFlight1_middleBox = binding.checkinMain.ivRecentFlightsFlight1.cbCheckinRVMiddle
        val cIFlight1_windowBox = binding.checkinMain.ivRecentFlightsFlight1.cbCheckinRVWindow
        val cIFlight1_loadingBeaver = binding.checkinMain.ivRecentFlightsFlight1.ivCheckinRVLoading
        val cIFlight1_seatFrame = binding.checkinMain.ivRecentFlightsFlight1.ivCheckinRVSeatFrame
        val cIFlight1_seat = binding.checkinMain.ivRecentFlightsFlight1.tvCheckinRVSeatLetter
        val cIFlight1_issueBoardingPass = binding.checkinMain.ivRecentFlightsFlight1.bttnCheckinRVIssueBoardingpass
        val cIFlight1_makeSelec = binding.checkinMain.ivRecentFlightsFlight1.tvCheckinRVMakeSelc

        val cIFlight2 = binding.checkinMain.readyForCIFlight2
        val cIFlight2_bg = binding.checkinMain.ivRecentFlightsFlight2.checkinRVConstraint
        val cIFlight2_ticketNbr = binding.checkinMain.ivRecentFlightsFlight2.tvCheckinRVBookingNbr
        val cIFlight2_cabinClass = binding.checkinMain.ivRecentFlightsFlight2.tvCheckinRVClass
        val cIFlight2_departCity = binding.checkinMain.ivRecentFlightsFlight2.tvCheckinRVDepartCity
        val cIFlight2_ariCity = binding.checkinMain.ivRecentFlightsFlight2.tvCheckinRVAriCity
        val cIFlight2_departDate = binding.checkinMain.ivRecentFlightsFlight2.tvCheckinRVTakeoffDate
        val cIFlight2_passFullname = binding.checkinMain.ivRecentFlightsFlight2.tvCheckinRVPassName
        val cIFlight2_checkinNow = binding.checkinMain.ivRecentFlightsFlight2.tvCheckinRVCheckinNow
        val cIFlight2_checkinArrow = binding.checkinMain.ivRecentFlightsFlight2.bttnCheckinRVArrow
        val cIFlight2_seatAssignment = binding.checkinMain.ivRecentFlightsFlight2.seatAssignment
        val cIFlight2_aisleBox = binding.checkinMain.ivRecentFlightsFlight2.cbCheckinRVAisle
        val cIFlight2_middleBox = binding.checkinMain.ivRecentFlightsFlight2.cbCheckinRVMiddle
        val cIFlight2_windowBox = binding.checkinMain.ivRecentFlightsFlight2.cbCheckinRVWindow
        val cIFlight2_loadingBeaver = binding.checkinMain.ivRecentFlightsFlight2.ivCheckinRVLoading
        val cIFlight2_seatFrame = binding.checkinMain.ivRecentFlightsFlight2.ivCheckinRVSeatFrame
        val cIFlight2_seat = binding.checkinMain.ivRecentFlightsFlight2.tvCheckinRVSeatLetter
        val cIFlight2_issueBoardingPass = binding.checkinMain.ivRecentFlightsFlight2.bttnCheckinRVIssueBoardingpass
        val cIFlight2_makeSelec = binding.checkinMain.ivRecentFlightsFlight2.tvCheckinRVMakeSelc

        val allBoardingPassesCard = binding.allBoardingpasses
        val allBoardingPassesHeader = binding.allBoardingpasses.completeBoardingCard.BP_recycler_header4
        val allBoardingPassesRecycler = binding.allBoardingpasses.BPRecycler

        val finalBoardingPassLayout = binding.finalBoardingPassLayout
        val finalBP = binding.finalBoardingPass.completeBoardingCard
        val finalBPgate = binding.finalBoardingPass.boardingcardGateNbr
        val finalBPpassFirstname = binding.finalBoardingPass.boardingcardPasFirstname
        val finalBPpassSurname = binding.finalBoardingPass.boardingcardPasSurname
        val finalBPseatNbr = binding.finalBoardingPass.boardingcardSeatNbr
        val finalBPdestination = binding.finalBoardingPass.boardingcardDestination
        val finalBPboardingTime = binding.finalBoardingPass.boardingcardBoardingTime
        val finalBPsaveBttn = binding.finalBoardingPass.boardingcardBttnSave
        val finalBPcloseBttn = binding.finalBoardingPass.boardingcardBttnClose



        //ADAPTER ASSIGNMENT FOR BOARDING PASS ISSUE:
        val bpAdapter = BoardingPassAdapter(listOf(), bookingViewModel, this)
        allBoardingPassesRecycler.adapter = bpAdapter



        //OBSERVER WATCHING AVL CHECKINS:
        bookingViewModel.nextCheckin.observe(
            viewLifecycleOwner,
            Observer {

                if (it != null) {
                    val booking = it
                    checkIn1 = false
                    checkIn2 = false


                    //NECESSARY VALUES:
                    cIFlight1.visibility = View.VISIBLE
                    cIFlight2.visibility = View.VISIBLE
                    cINoAvl.visibility = View.GONE

                    cIFlight1_ticketNbr.text = "BEAVER BOOKING No:  ${it.flight1_flightNbr}"
                    cIFlight1_cabinClass.text = it.flight1_cabinclass
                    cIFlight1_departCity.text = it.flight1_departCity
                    cIFlight1_ariCity.text = it.flight1_ariCity
                    cIFlight1_departDate.text = it.flight1_departDate
                    cIFlight1_passFullname.text = "${it.flight1_passFirstname} ${it.flight1_passSurname}"

                    cIFlight2_ticketNbr.text = "BEAVER BOOKING No:  ${it.flight2_flightNbr}"
                    cIFlight2_cabinClass.text = it.flight2_cabinclass
                    cIFlight2_departCity.text = it.flight2_departCity
                    cIFlight2_ariCity.text = it.flight2_ariCity
                    cIFlight2_departDate.text = it.flight2_departDate
                    cIFlight2_passFullname.text = "${it.flight1_passFirstname} ${it.flight1_passSurname}"

                    val cIFlight1_passFirstName = it.flight1_passFirstname
                    val cIFlight1_passSurname = it.flight1_passSurname
                    val cIFlight1_destinationIata = it.flight1_ariIATA
                    val cIFlight1_boardingtime = it.flight1_takeoffTime

                    val cIFlight2_passFirstName = it.flight2_passFirstname
                    val cIFlight2_passSurname = it.flight2_passSurname
                    val cIFlight2_destinationIata = it.flight2_ariIATA
                    val cIFlight2_boardingtime = it.flight2_takeoffTime



                    //ANIMATION UTILS:
                    val arrowRotator1UP = ObjectAnimator.ofFloat(
                        cIFlight1_checkinArrow,
                        View.ROTATION,
                        180f)
//                arrowRotatorUP.duration = 2000

                    val arrowRotator1DOWN = ObjectAnimator.ofFloat(
                        cIFlight1_checkinArrow,
                        View.ROTATION,
                        -90f)
//                arrowRotatorDOWN.duration = 2000

                    val arrowRotator2UP = ObjectAnimator.ofFloat(
                        cIFlight2_checkinArrow,
                        View.ROTATION,
                        180f)
//                arrowRotatorUP.duration = 2000

                    val arrowRotator2DOWN = ObjectAnimator.ofFloat(
                        cIFlight2_checkinArrow,
                        View.ROTATION,
                        -90f)
//                arrowRotatorDOWN.duration = 2000



                    //CLICK LISTENERS FOR FIRST FLIGHT BOARDING PASS:
                    cIFlight1_checkinArrow.setOnClickListener {
                        if (cIFlight1_seatAssignment.visibility == View.GONE) {
                            arrowRotator1UP.start()
                            cIFlight1_seatAssignment.visibility = View.VISIBLE
                            TransitionManager.beginDelayedTransition(cIFlight1, AutoTransition())
                            cIFlight1_bg.setCardBackgroundColor(resources.getColor(R.color.purple_200))
                            cIFlight1_bg.startAnimation(fadeIN)


                            //AISLE SEAT CHECKBOX:
                            cIFlight1_aisleBox.setOnCheckedChangeListener { _, isChecked ->
                                cIFlight1_seatFrame.visibility = View.GONE
                                cIFlight1_seat.visibility = View.GONE
                                cIFlight1_issueBoardingPass.visibility = View.GONE
                                cIFlight1_makeSelec.startAnimation(fadeOUT)
                                cIFlight1_makeSelec.visibility = View.GONE

                                cIFlight1_middleBox.isChecked = false
                                cIFlight1_windowBox.isChecked = false

                                cIFlight1_loadingBeaver.visibility = View.VISIBLE
                                cIFlight1_loadingBeaver.startAnimation(rotate)

                                Handler().postDelayed({
                                    cIFlight1_loadingBeaver.startAnimation(fadeOUT)
                                    cIFlight1_loadingBeaver.visibility = View.GONE
                                    cIFlight1_seat.text = aisleSeatGenerator()
                                    cIFlight1_seatFrame.visibility = View.VISIBLE
                                    cIFlight1_seat.visibility = View.VISIBLE
                                    cIFlight1_issueBoardingPass.visibility = View.VISIBLE
                                }, 2000)
                            }


                            //MIDDLE SEAT CHECKBOX:
                            cIFlight1_middleBox.setOnCheckedChangeListener { _, isChecked ->
                                cIFlight1_seatFrame.visibility = View.GONE
                                cIFlight1_seat.visibility = View.GONE
                                cIFlight1_issueBoardingPass.visibility = View.GONE
                                cIFlight1_makeSelec.startAnimation(fadeOUT)
                                cIFlight1_makeSelec.visibility = View.GONE

                                cIFlight1_aisleBox.isChecked = false
                                cIFlight1_windowBox.isChecked = false

                                cIFlight1_loadingBeaver.visibility = View.VISIBLE
                                cIFlight1_loadingBeaver.startAnimation(rotate)

                                Handler().postDelayed({
                                    cIFlight1_loadingBeaver.startAnimation(fadeOUT)
                                    cIFlight1_loadingBeaver.visibility = View.GONE
                                    cIFlight1_seat.text = middleSeatGenerator()
                                    cIFlight1_seatFrame.visibility = View.VISIBLE
                                    cIFlight1_seat.visibility = View.VISIBLE
                                    cIFlight1_issueBoardingPass.visibility = View.VISIBLE
                                }, 2000)
                            }


                            //WINDOW SEAT CHECKBOX:
                            cIFlight1_windowBox.setOnCheckedChangeListener { _, isChecked ->
                                cIFlight1_seatFrame.visibility = View.GONE
                                cIFlight1_seat.visibility = View.GONE
                                cIFlight1_issueBoardingPass.visibility = View.GONE
                                cIFlight1_makeSelec.startAnimation(fadeOUT)
                                cIFlight1_makeSelec.visibility = View.GONE

                                cIFlight1_middleBox.isChecked = false
                                cIFlight1_aisleBox.isChecked = false

                                cIFlight1_loadingBeaver.visibility = View.VISIBLE
                                cIFlight1_loadingBeaver.startAnimation(rotate)

                                Handler().postDelayed({
                                    cIFlight1_loadingBeaver.startAnimation(fadeOUT)
                                    cIFlight1_loadingBeaver.visibility = View.GONE
                                    cIFlight1_seat.text = windowSeatGenerator()
                                    cIFlight1_seatFrame.visibility = View.VISIBLE
                                    cIFlight1_seat.visibility = View.VISIBLE
                                    cIFlight1_issueBoardingPass.visibility = View.VISIBLE
                                }, 2000)
                            }


                        } else {
                            cIFlight1_seatAssignment.visibility = View.GONE
                            arrowRotator1DOWN.start()
                            TransitionManager.beginDelayedTransition(cIFlight1, AutoTransition())
                            cIFlight1_bg.setCardBackgroundColor(resources.getColor(R.color.white))
                            cIFlight1_bg.startAnimation(fadeIN)
                        }


                        //CLICK LISTENER TO ISSUE BOARDING PASS FOR FIRST FLIGHT:
                        cIFlight1_issueBoardingPass.setOnClickListener {
                            val cIFlight1_gate = gateGenerator()
                            val cIFlight1_assignedSeat = cIFlight1_seat.text.toString()


                            //SAVING RELATED VALUES FOR FURTHER USAGE:
                            val newBP =
                                FinalBoardingPass(cIFlight1_passFirstName,
                                    cIFlight1_passSurname,
                                    cIFlight1_destinationIata,
                                    cIFlight1_boardingtime,
                                    cIFlight1_gate,
                                    cIFlight1_assignedSeat)

                            bookingViewModel.saveIssuedBoardingPass(newBP)


                            //SETTING CHECKIN FOR FIRST FLIGHT AND CLOSING VIEW:
                            TransitionManager.beginDelayedTransition(cICard, AutoTransition())
                            cIFlight1.visibility = View.GONE

                            checkIn1 = true
                            if (checkIn1 && checkIn2) {
                                booking.isCheckedin = true
                                bookingViewModel.updateBooking(booking)
                                bookingViewModel.getNextCheckin()
                                TransitionManager.beginDelayedTransition(cICard, AutoTransition())
                                cINoAvl.visibility = View.VISIBLE

                            }
                        }
                    }



                    //CLICK LISTENERS FOR SECOND FLIGHT BOARDING PASS:
                    cIFlight2_checkinArrow.setOnClickListener {
                        if (cIFlight2_seatAssignment.visibility == View.GONE) {
                            arrowRotator2UP.start()
                            cIFlight2_seatAssignment.visibility = View.VISIBLE
                            TransitionManager.beginDelayedTransition(cIFlight2, AutoTransition())
                            cIFlight2_bg.setCardBackgroundColor(resources.getColor(R.color.purple_200))
                            cIFlight2_bg.startAnimation(fadeIN)


                            //AISLE SEAT CHECKBOX:
                            cIFlight2_aisleBox.setOnCheckedChangeListener { _, isChecked ->
                                cIFlight2_seatFrame.visibility = View.GONE
                                cIFlight2_seat.visibility = View.GONE
                                cIFlight2_issueBoardingPass.visibility = View.GONE
                                cIFlight2_makeSelec.startAnimation(fadeOUT)
                                cIFlight2_makeSelec.visibility = View.GONE

                                cIFlight2_middleBox.isChecked = false
                                cIFlight2_windowBox.isChecked = false

                                cIFlight2_loadingBeaver.visibility = View.VISIBLE
                                cIFlight2_loadingBeaver.startAnimation(rotate)

                                Handler().postDelayed({
                                    cIFlight2_loadingBeaver.startAnimation(fadeOUT)
                                    cIFlight2_loadingBeaver.visibility = View.GONE
                                    cIFlight2_seat.text = aisleSeatGenerator()
                                    cIFlight2_seatFrame.visibility = View.VISIBLE
                                    cIFlight2_seat.visibility = View.VISIBLE
                                    cIFlight2_issueBoardingPass.visibility = View.VISIBLE
                                }, 2000)
                            }


                            //MIDDLE SEAT CHECKBOX:
                            cIFlight2_middleBox.setOnCheckedChangeListener { _, isChecked ->
                                cIFlight2_seatFrame.visibility = View.GONE
                                cIFlight2_seat.visibility = View.GONE
                                cIFlight2_issueBoardingPass.visibility = View.GONE
                                cIFlight2_makeSelec.startAnimation(fadeOUT)
                                cIFlight2_makeSelec.visibility = View.GONE

                                cIFlight2_aisleBox.isChecked = false
                                cIFlight2_windowBox.isChecked = false

                                cIFlight2_loadingBeaver.visibility = View.VISIBLE
                                cIFlight2_loadingBeaver.startAnimation(rotate)

                                Handler().postDelayed({
                                    cIFlight2_loadingBeaver.startAnimation(fadeOUT)
                                    cIFlight2_loadingBeaver.visibility = View.GONE
                                    cIFlight2_seat.text = middleSeatGenerator()
                                    cIFlight2_seatFrame.visibility = View.VISIBLE
                                    cIFlight2_seat.visibility = View.VISIBLE
                                    cIFlight2_issueBoardingPass.visibility = View.VISIBLE
                                }, 2000)
                            }


                            //WINDOW SEAT CHECKBOX:
                            cIFlight2_windowBox.setOnCheckedChangeListener { _, isChecked ->
                                cIFlight2_seatFrame.visibility = View.GONE
                                cIFlight2_seat.visibility = View.GONE
                                cIFlight2_issueBoardingPass.visibility = View.GONE
                                cIFlight2_makeSelec.startAnimation(fadeOUT)
                                cIFlight2_makeSelec.visibility = View.GONE

                                cIFlight2_middleBox.isChecked = false
                                cIFlight2_aisleBox.isChecked = false

                                cIFlight2_loadingBeaver.visibility = View.VISIBLE
                                cIFlight2_loadingBeaver.startAnimation(rotate)

                                Handler().postDelayed({
                                    cIFlight2_loadingBeaver.startAnimation(fadeOUT)
                                    cIFlight2_loadingBeaver.visibility = View.GONE
                                    cIFlight2_seat.text = windowSeatGenerator()
                                    cIFlight2_seatFrame.visibility = View.VISIBLE
                                    cIFlight2_seat.visibility = View.VISIBLE
                                    cIFlight2_issueBoardingPass.visibility = View.VISIBLE
                                }, 2000)
                            }



                        } else {
                            cIFlight2_seatAssignment.visibility = View.GONE
                            arrowRotator2DOWN.start()
                            TransitionManager.beginDelayedTransition(cIFlight2, AutoTransition())
                            cIFlight2_bg.setCardBackgroundColor(resources.getColor(R.color.white))
                            cIFlight2_bg.startAnimation(fadeIN)
                        }


                        //CLICK LISTENER TO ISSUE BOARDING PASS FOR FIRST FLIGHT:
                        cIFlight2_issueBoardingPass.setOnClickListener {
                            val cIFlight2_gate = gateGenerator()
                            val cIFlight2_assignedSeat = cIFlight2_seat.text.toString()


                            //SAVING RELATED VALUES FOR FURTHER USAGE:
                            val newBP2 =
                                FinalBoardingPass(cIFlight2_passFirstName,
                                    cIFlight2_passSurname,
                                    cIFlight2_destinationIata,
                                    cIFlight2_boardingtime,
                                    cIFlight2_gate,
                                    cIFlight2_assignedSeat)

                            bookingViewModel.saveIssuedBoardingPass(newBP2)


                            //SETTING CHECKIN FOR SECOND FLIGHT AND CLOSING VIEW:
                            cIFlight2.visibility = View.GONE
                            TransitionManager.beginDelayedTransition(cICard, AutoTransition())

                            checkIn2 = true
                            if (checkIn1 && checkIn2) {
                                booking.isCheckedin = true
                                bookingViewModel.updateBooking(booking)
                                bookingViewModel.getNextCheckin()
                                TransitionManager.beginDelayedTransition(cICard, AutoTransition())
                                cINoAvl.visibility = View.VISIBLE


                            }
                        }
                    }

                } else {
                    cINoAvl.visibility = View.VISIBLE
                    cIFlight1.visibility = View.GONE
                    cIFlight2.visibility = View.GONE
                }
            }
        )



        //OBSERVER FOR FINAL BOARDING PASSES:
        bookingViewModel.finalBoardingPass.observe(
            viewLifecycleOwner,
            Observer {
                if (it != null) {


                    //PERMISSIONS TO WRITE AND READ ON STORAGE
                    ActivityCompat.requestPermissions(requireActivity(),
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        1)

                    ActivityCompat.requestPermissions(requireActivity(),
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        1)



                    //VALUES RECEIVED FROM OBSERVED "FINALBOARDINGPASS":
                    val boardingPass = it
                    var gate = boardingPass.gate.toString()
                    var firstName = boardingPass.passFirstname
                    var surname = boardingPass.passSurname
                    var seat = boardingPass.assignedSeat
                    var destination = boardingPass.destinationIata
                    var boarding = boardingPass.boardingtime.dropLast(3)



                    //SETTING BOARDING CARD WITH PROVIDED VALUES FROM VIEWMODEL:
                    finalBPgate.setText(gate)
                    finalBPpassFirstname.text = firstName
                    finalBPpassSurname.text = surname
                    finalBPseatNbr.text = seat
                    finalBPdestination.text = destination
                    finalBPboardingTime.text = boarding


                    //ANIMATING VISIBILITY OF BOARDING PASS LAYOUT:
                    finalBoardingPassLayout.startAnimation(fadeIN)
                    finalBoardingPassLayout.visibility = View.VISIBLE


                    //CLICK LISTENERS FOR SETTING VISIBILITY OF BOARDING PASS LAYOUT:
                    finalBPcloseBttn.setOnClickListener {
                        finalBoardingPassLayout.startAnimation(fadeOUT)
                        finalBoardingPassLayout.visibility = View.GONE
                    }

                    finalBPsaveBttn.setOnClickListener {
                        saveBPtoAlbum()
                        finalBoardingPassLayout.startAnimation(fadeOUT)
                        finalBoardingPassLayout.visibility = View.GONE
                    }
                }
            }
        )


        //OBSERVER FOR FINAL BOARDING PASSES OVERVIEW COLLECTION IN SEPARATE RECYCLERVIEW:
        bookingViewModel.boardingpassList.observe(
            viewLifecycleOwner,
            Observer {
                if (it != null) {
                    bpAdapter.submitBoardingPassList(it)
                    allBoardingPassesHeader.visibility = View.GONE
                    allBoardingPassesRecycler.visibility = View.VISIBLE
                }
            }
        )


        //UNUSED CODE
        /*
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

                    finalBP.setOnClickListener {
                        finalBoardingPassLayout.visibility = View.GONE


                        allBoardingPassesRecycler.adapter = BoardingPassAdapter(boardingPass, bookingViewModel, this)
                    }




                }
            }
        )




//        bookingViewModel.bookingList.observe(
//            viewLifecycleOwner,
//            Observer {
//                fastCheckinRecycler.adapter = FastCheckinAdapter(it,  bookingViewModel, this)
//            }
//        )


//        bookingViewModel.isBoardingPassIssued.
//        if (bookingViewModel.isBoardingPassIssued){
//            finalBPpassFirstname.text = bookingViewModel.passFirstname
//            finalBPpassSurname.text = bookingViewModel.passSurname
//            finalBPdestination.text = bookingViewModel.destinationIata
//            finalBPboardingTime.text = bookingViewModel.boardingtime
//            finalBPgate.text = bookingViewModel.gate
//            finalBPseatNbr.text = bookingViewModel.assignedSeat
//        }

        */



    }



    //METHOD TO SAVE BOARDING PASS TO PHOTO ALBUM:
    private fun saveBPtoAlbum() {
        val bitmap = getScreenShot(binding.finalBoardingPass.completeBoardingCard)
        if (bitmap != null) {
            saveMediaToStorage(bitmap)
        }
    }



    //METHOD TO CREATE A SCREENSHOT OF SHOWING BOARDING PASS:
    private fun getScreenShot(view: View): Bitmap? {
        var targetBmp: Bitmap? = null
        var screenshot: Bitmap? = null

        try {
            screenshot = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
            targetBmp = screenshot.copy(Bitmap.Config.ARGB_8888, true)

            var canvas = Canvas(targetBmp)
            view.draw(canvas)

        } catch (e: Exception) {
            Log.e("GFG", "Failed to capture boarding card because:" + e.message)
        }

        return targetBmp
    }



    //METHOD TO SAVE ACTUAL SCREENSHOT TO LOCAL STORAGE:
    private fun saveMediaToStorage(bitmap: Bitmap) {
        var resolver = requireContext().contentResolver
        val filename = "${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            resolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }

        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }


        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(requireContext(),
                "Boarding pass saved to your album",
                Toast.LENGTH_SHORT).show()
        }
    }



    //METHOD TO GENERATE AISLE SEAT NBR:
    private fun aisleSeatGenerator(): String {

        val aisleLetters = listOf("C", "D")
        val aisleRow = (24..58)

        val generatedAisleSeat = "${aisleLetters.random()}${aisleRow.random()}"

        return generatedAisleSeat
    }



    //METHOD TO GENERATE MIDDLE SEAT NBR:
    private fun middleSeatGenerator(): String {

        val middleLetters = listOf("B", "E")
        val middleRow = (24..58)

        val generatedMiddleSeat = "${middleLetters.random()}${middleRow.random()}"

        return generatedMiddleSeat
    }


    //METHOD TO GENERATE WINDOW SEAT NBR:
    private fun windowSeatGenerator(): String {

        val windowLetters = listOf("A", "F")
        val windowRow = (24..58)

        val generatedWindowSeat = "${windowLetters.random()}${windowRow.random()}"

        return generatedWindowSeat
    }


    //METHOD TO GENERATE GATE NBR:
    private fun gateGenerator(): String {

        val gateLetter = listOf("A", "B", "C", "D", "E", "F", "G")
        val gateNbr = (1..33)

        val generatedGate = "${gateLetter.random()}${gateNbr.random()}"

        return generatedGate
    }


    //UNUSED METHODS:
    override fun openFlight() {
    }

    override fun openReturnFlight(flight: FlightOffer, bookingNbr: String) {
    }

    override fun openPayment(
        depIata: String,
        ariIata: String,
        returnFlight: FlightOffer,
        returnBookingNbr: String) {
    }



    //METHOD TO RESET THE VIEW OF THE BOARDING PASS:
    override fun onDestroy() {
        super.onDestroy()
        bookingViewModel.resetBP()

    }

}

