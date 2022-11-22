package com.example.beaverairlines.home.childFragments

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Handler
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper

import com.example.beaverairlines.R
import com.example.beaverairlines.ViewModel
import com.example.beaverairlines.adapter.*
import com.example.beaverairlines.data.FlightOffer
import com.example.beaverairlines.data.Iata
import com.example.beaverairlines.data.model.CabinClassSource
import com.example.beaverairlines.databinding.FragmentBookBinding
import com.example.beaverairlines.utils.BookInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.book3_card.*
import kotlinx.android.synthetic.main.book3_card.expandCard
import kotlinx.android.synthetic.main.book3_card.view.*
import kotlinx.android.synthetic.main.fragment_book.view.*
import kotlinx.android.synthetic.main.pay_flights.*
import kotlinx.android.synthetic.main.pay_flights.view.*
import kotlinx.android.synthetic.main.select_flights.view.*
import kotlinx.android.synthetic.main.select_flights.view.payCARDConstraint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class BookFragment : Fragment(), BookInterface {

    private lateinit var binding: FragmentBookBinding

    private lateinit var departureDate: TextView
    private lateinit var returnDate: TextView
    private lateinit var adultPassengers: TextView
    private lateinit var infantPassengers: TextView
    private var adultCounter: Int = 1
    private var infantCounter: Int = 0

    private lateinit var iataArrayList: ArrayList<Iata>
    private lateinit var tempIataArrayList: ArrayList<Iata>

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private var isReturnFlight = false
    private val flightViewModel: ViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentBookBinding.inflate(inflater)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val airportAdapter = AirportAdapter()
        //val departureAirport = binding.bigBookCard.cvBookField.tv_departCitySelect.setAdapter(airportAdapter)

        val selectDepartureCity: AutoCompleteTextView = view.findViewById(R.id.tv_departCitySelect)
        val selectArrivalCity: AutoCompleteTextView = view.findViewById(R.id.tv_arriveCitySelect)
        val arrivalIata: TextView = view.findViewById(R.id.tv_IATAarrival)
        val departureIata: TextView = view.findViewById(R.id.tv_IATAdeparture)


        tempIataArrayList = arrayListOf<Iata>()


        val iata = flightViewModel.iata

        tempIataArrayList.addAll(iata)

        //val iataAdapter = IataAdapter(iata)
        //val iataAdapter2 = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, iata)
        //selectArrivalCity.setAdapter(iataAdapter2)

        context?.let { ctx ->
            val iataArrayAdapter2 = IataArrayAdapter(ctx, R.layout.iata_item, iata)
            selectDepartureCity.setAdapter(iataArrayAdapter2)
            selectDepartureCity.setOnItemClickListener { parent, _, position, _ ->
                val iata = iataArrayAdapter2.getItem(position) as Iata?
                selectDepartureCity.setText(iata?.name)
                departureIata.setText(iata?.iata)
            }

        }

        context?.let { ctx ->
            val iataArrayAdapter = IataArrayAdapter(ctx, R.layout.iata_item, iata)
            selectArrivalCity.setAdapter(iataArrayAdapter)
            selectArrivalCity.setOnItemClickListener { parent, _, position, _ ->
                val iata = iataArrayAdapter.getItem(position) as Iata?
                selectArrivalCity.setText(iata?.name)
                arrivalIata.setText(iata?.iata)
            }

        }


        //binding.bigBookCard.cvBookField.tv_arriveCitySelect.setadapter(ataAdapter2)
//        selectDepartureCity.setOnItemClickListener { adapterView, view, i, l -> }
/*
        selectDepartureCity.setOnClickListener {

            val slideInRight2 = AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.slide_in_right)

            val iataDialogBinding = layoutInflater.inflate(R.layout.diaolog_progress,null)

            val iataDialog = Dialog(requireActivity())
            iataDialog.setContentView(iataDialogBinding)

            iataDialog.setCancelable(true)
            iataDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            iataDialog.expandCard.startAnimation(slideInRight2)
            iataDialog.show()

            iataDialog.expandCard.rv_iataCodes.adapter = iataAdapter

            iataDialog.expandCard.sv_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                   return false

                }

                override fun onQueryTextChange(newText: String?): Boolean {
                 tempIataArrayList.clear()
                    val searchText = newText!!.lowercase(Locale.getDefault())
                    if (searchText.isNotEmpty()){

                        iata.forEach{
                            if (it.name.lowercase(Locale.getDefault()).contains(searchText)){

                                tempIataArrayList.add(it)
                            }
                        }

                        iataAdapter.submitList(tempIataArrayList)


                    } else {

                        tempIataArrayList.clear()
                        tempIataArrayList.addAll(iata)
                        iataAdapter.submitList(tempIataArrayList)
                    }

                    return false
                }

            })
        }

 */



        departureDate = view.findViewById(R.id.tv_depDateSelect)
        returnDate = view.findViewById(R.id.tv_arriveDateSelect)
        val calender = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calender.set(Calendar.YEAR, year)
            calender.set(Calendar.MONTH, month)
            calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updatelabel(calender)
        }

        val datePicker2 = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calender.set(Calendar.YEAR, year)
            calender.set(Calendar.MONTH, month)
            calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updatelabel2(calender)
        }

        departureDate.setOnClickListener {
            DatePickerDialog(requireContext(),
                datePicker,
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH)).show()
        }

        returnDate.setOnClickListener {
            DatePickerDialog(requireContext(),
                datePicker2,
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH)).show()
        }


/*
        binding.bigBookCard.cvBookField.tv_departCitySelect.setOnClickListener {
           //val term = binding.bigBookCard.cvBookField.tv_departCitySelect.text.toString()
            val term = selectDepCity.text.toString()
            airportViewModel.searchAirports(term)
        }

        binding.bigBookCard.cvBookField.tv_arriveCitySelect.setOnClickListener {
            //val term = binding.bigBookCard.cvBookField.tv_arriveCitySelect.text.toString()
            val term = selectAriCity.text.toString()
            airportViewModel.searchAirports(term)
        }

 */


        val cabinClasses = CabinClassSource().loadCabinClass()
        val ccAdapter = CabinClassAdapter(cabinClasses)
        binding.bigBookCard.rvCabinClass.adapter = ccAdapter

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.bigBookCard.rvCabinClass)

        binding.bigBookCard.expandConstraint2.tv_cabinSelectedText.text =
            "Please select a Cabin Class"

        //Hier wird der die vom ccAdapter übergebende "Cabin Class"- Unit aufgerufen:
        ccAdapter.setOnItemClickListener {
            binding.bigBookCard.expandConstraint2.tv_cabinSelectedText.text = it.title
        }


        adultPassengers = view.findViewById(R.id.tv_adultAmount)
        infantPassengers = view.findViewById(R.id.tv_infantAmount)

        adultPassengers.text = adultCounter.toString()
        infantPassengers.text = infantCounter.toString()

        binding.bigBookCard.expandConstraint3.bttn_adultPlus.setOnClickListener {
            adultCounter++
            adultPassengers.text = adultCounter.toString()
        }

        binding.bigBookCard.expandConstraint3.bttn_adultMinus.setOnClickListener {
            adultCounter--
            if (adultCounter < 0) {
                adultCounter = 0
            }
            adultPassengers.text = adultCounter.toString()
        }

        binding.bigBookCard.expandConstraint3.bttn_infantPlus.setOnClickListener {
            infantCounter++
            infantPassengers.text = infantCounter.toString()
        }

        binding.bigBookCard.expandConstraint3.bttn_infantMinus.setOnClickListener {
            infantCounter--
            if (infantCounter < 0) {
                infantCounter = 0
            }
            infantPassengers.text = infantCounter.toString()
        }


        val slideInRight = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.slide_in_right)


        binding.bigBookCard.ibArrow1.setOnClickListener {
            if (expandConstraint1.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(expandCard, AutoTransition())
                expandConstraint1.visibility = View.VISIBLE
                iv_planeIndicator.visibility = View.VISIBLE
                iv_planeIndicator.startAnimation(slideInRight)
                ib_arrow1.animate().setDuration(2).rotationBy(108f).start()
                ib_arrow1.visibility = View.GONE
                iv_redlineIndicator.visibility = View.VISIBLE
                iv_redlineIndicator.startAnimation(slideInRight)
            } else {
                TransitionManager.beginDelayedTransition(expandCard, AutoTransition())
                expandConstraint1.visibility = View.GONE
                ib_arrow1.visibility = View.GONE
                ib_arrow1.animate().setDuration(2).rotationBy(108f).start()

            }
        }

        binding.bigBookCard.ibArrow2.setOnClickListener {
            if (expandConstraint2.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(expandCard, AutoTransition())
                expandConstraint2.visibility = View.VISIBLE
                ib_arrow2.animate().setDuration(2).rotationBy(108f).start()
                ib_arrow2.visibility = View.GONE
                iv_redlineIndicator2.visibility = View.VISIBLE
                iv_redlineIndicator2.startAnimation(slideInRight)
                iv_planeIndicator.startAnimation(slideInRight)
                iv_planeIndicator.translationX = iv_planeIndicator.translationX + 230
            } else {
                TransitionManager.beginDelayedTransition(expandCard, AutoTransition())
                expandConstraint2.visibility = View.GONE
                ib_arrow2.visibility = View.GONE
                ib_arrow2.animate().setDuration(2).rotationBy(108f).start()

            }
        }

        binding.bigBookCard.ibArrow3.setOnClickListener {
            if (expandConstraint3.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(expandCard, AutoTransition())
                expandConstraint3.visibility = View.VISIBLE
                ib_arrow3.animate().setDuration(2).rotationBy(108f).start()
                ib_arrow3.visibility = View.GONE
                iv_redlineIndicator3.visibility = View.VISIBLE
                iv_redlineIndicator3.startAnimation(slideInRight)
                iv_planeIndicator.startAnimation(slideInRight)
                iv_planeIndicator.translationX = iv_planeIndicator.translationX + 270
            } else {
                TransitionManager.beginDelayedTransition(expandCard, AutoTransition())
                expandConstraint3.visibility = View.GONE
                ib_arrow3.visibility = View.GONE
                ib_arrow3.animate().setDuration(2).rotationBy(108f).start()

            }
        }

        binding.bigBookCard.ibArrow4.setOnClickListener {
            if (expandConstraint4.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(expandCard, AutoTransition())
                expandConstraint4.visibility = View.VISIBLE
                //binding.constraintBigBookCard.bttn2.visibility = View.VISIBLE
                ib_arrow4.animate().setDuration(2).rotationBy(108f).start()
                ib_arrow4.visibility = View.GONE
                iv_redlineIndicator4.visibility = View.VISIBLE
                iv_redlineIndicator4.startAnimation(slideInRight)
                iv_planeIndicator.startAnimation(slideInRight)
                iv_planeIndicator.translationX = iv_planeIndicator.translationX + 230
                //binding.constraintBigBookCard.bttn2.startAnimation(slideInRight)
            } else {
                TransitionManager.beginDelayedTransition(expandCard, AutoTransition())
                expandConstraint4.visibility = View.GONE
                //binding.constraintBigBookCard.bttn2.visibility = View.GONE
                ib_arrow4.visibility = View.GONE
                ib_arrow4.animate().setDuration(2).rotationBy(108f).start()

            }
        }






        binding.constraintBigBookCard.bttn_searchFlights.setOnClickListener {

            flightViewModel.depIata =
                binding.bigBookCard.cvBookField.tv_IATAdeparture.text.toString()
            flightViewModel.ariIata = binding.bigBookCard.cvBookField.tv_IATAarrival.text.toString()




            binding.constraintBigBookCard.bttn2.visibility = View.VISIBLE
            val scaleBttnX = PropertyValuesHolder.ofFloat(View.SCALE_X, 10f)
            val scaleBttnY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 13f)
            val animatorBttn =
                ObjectAnimator.ofPropertyValuesHolder(binding.constraintBigBookCard.bttn2,
                    scaleBttnX,
                    scaleBttnY)
            animatorBttn.duration = 200
            //animatorS.repeatCount = 1
            //animatorS.repeatMode = ObjectAnimator.REVERSE
            //animatorS.interpolator = BounceInterpolator()
            animatorBttn.start()


            val layoutIn = AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.bttn_slide_in)
//                binding.selectFlightsCard.visi
//                binding.selectFlightsCard.startAnimation(layoutIn)

            binding.selectFlightsCard.loadingConstraint.visibility = View.VISIBLE
            binding.selectFlightsCard.loadingConstraint.startAnimation(layoutIn)

            val earthIn = AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.bttn_slide_in)
            binding.selectFlightsCard.pbEarth.visibility = View.VISIBLE

            val animatorY = ObjectAnimator.ofFloat(binding.selectFlightsCard.pbEarth,
                View.TRANSLATION_Y,
                -1050f)
            animatorY.duration = 2000

            val animatorX =
                ObjectAnimator.ofFloat(binding.selectFlightsCard.pbEarth, View.TRANSLATION_X, -400f)
            animatorX.duration = 1000
            animatorX.repeatCount = 1
            animatorX.repeatMode = ObjectAnimator.REVERSE

            var toAlpha = 1f
            if (binding.selectFlightsCard.pbEarth.alpha == 0f) {
                toAlpha = 1f
            }

            val animatorF =
                ObjectAnimator.ofFloat(binding.selectFlightsCard.pbEarth, View.ALPHA, toAlpha)
            animatorF.duration = 500
            animatorF.start()


            val scaleSX = PropertyValuesHolder.ofFloat(View.SCALE_X, 10f)
            val scaleSY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 10f)
            val animatorS =
                ObjectAnimator.ofPropertyValuesHolder(binding.selectFlightsCard.pbEarth,
                    scaleSX,
                    scaleSY)
            animatorS.duration = 1000
            animatorS.start()


            val set = AnimatorSet()
            set.playTogether(animatorX, animatorY, animatorS)
            set.start()
/*
            Handler().postDelayed({
                val fadeIN = AnimationUtils.loadAnimation(
                    requireContext(),
                    R.anim.fade_in)
            //binding.selectFlightsCard.ivLoadingBeaver.visibility = View.VISIBLE
            //binding.selectFlightsCard.ivLoadingBeaver.startAnimation(fadeIN)
                binding.selectFlightsCard.tvLoadingSearchText.startAnimation(fadeIN)
                binding.selectFlightsCard.tvLoadingSearchText.visibility = View.VISIBLE
            }, 800)

            Handler().postDelayed({
            binding.selectFlightsCard.ivLoadingBeaver.animate().apply {
                duration = 1100
                rotationXBy(360f)
            }.start()

            }, 1800)


 */




/*
            Handler().postDelayed({
                val textIN = AnimationUtils.loadAnimation(
                    requireContext(),
                    R.anim.text_slide_in)
                    textIN.duration = 1000


                binding.selectFlightsCard.tvLoadingFrom.startAnimation(textIN)
                binding.selectFlightsCard.tvLoadingFrom.visibility = View.VISIBLE



                binding.selectFlightsCard.tvLoadingTo.startAnimation(textIN)
                binding.selectFlightsCard.tvLoadingTo.visibility = View.VISIBLE

                binding.selectFlightsCard.tvLoadingAri.text =
                binding.bigBookCard.cvBookField.tv_IATAarrival.text.toString()
                binding.selectFlightsCard.tvLoadingAri.visibility = View.VISIBLE
                binding.selectFlightsCard.tvLoadingAri.startAnimation(textIN)

            }, 1000)

 */






/*
//            Handler().postDelayed({
                flightViewModel.started.observe(
                    viewLifecycleOwner,
                    androidx.lifecycle.Observer {
                        if (it) {
                            val resultsIN = AnimationUtils.loadAnimation(
                                requireContext(),
                                R.anim.text_slide_in)
                            //resultsIN.duration = 1000
                            binding.selectFlightsCard.BLA.iv_backgroundBttnSheet.visibility = View.VISIBLE
                            binding.selectFlightsCard.BLA.iv_backgroundBttnSheet.startAnimation(resultsIN)
                                    }
                                }
                            )
                        }

 */


            Handler().postDelayed({
                flightViewModel.status.observe(
                    viewLifecycleOwner,
                    androidx.lifecycle.Observer {

                        if (it) {

                            val beaverOut = AnimationUtils.loadAnimation(
                                requireContext(),
                                R.anim.fade_out)
                            //beaverOut.duration = 300
                            //binding.selectFlightsCard.ivLoadingBeaver.startAnimation(beaverOut)
                            binding.selectFlightsCard.tvLoadingSearchText.startAnimation(beaverOut)

                            //binding.selectFlightsCard.ivLoadingBeaver.visibility = View.GONE
                            binding.selectFlightsCard.tvLoadingSearchText.visibility = View.GONE

                            val resultsIN = AnimationUtils.loadAnimation(
                                requireContext(),
                                R.anim.text_slide_in_from_bttm)
                            //resultsIN.duration = 1000
                            binding.selectFlightsCard.cvResultsFirst.visibility = View.VISIBLE
                            binding.selectFlightsCard.cvResultsFirst.startAnimation(
                                resultsIN)
                            binding.selectFlightsCard.cvResultsFirst.rv_firstFlightResultsList.visibility =
                                View.VISIBLE
                            binding.selectFlightsCard.cvResultsFirst.rv_firstFlightResultsList.startAnimation(
                                resultsIN)



                        }
                    }
                )
            }, 800)

            flightViewModel.started.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer {
                    if (it) {
                        val resultsOUT = AnimationUtils.loadAnimation(
                            requireContext(),
                            R.anim.bttnbar_slide_out)

                        val textIN = AnimationUtils.loadAnimation(
                            requireContext(),
                            R.anim.text_slide_in)
                        textIN.duration = 1000


                        binding.selectFlightsCard.loadingConstraint.tv_loadingDep.startAnimation(resultsOUT)
                        binding.selectFlightsCard.loadingConstraint.tv_loadingDep.visibility = View.INVISIBLE

                        binding.selectFlightsCard.loadingConstraint.tv_loadingAri.startAnimation(resultsOUT)
                        binding.selectFlightsCard.loadingConstraint.tv_loadingAri.visibility = View.INVISIBLE

                        binding.selectFlightsCard.loadingConstraint.tv_loadingSearchText.visibility = View.INVISIBLE
                        binding.selectFlightsCard.loadingConstraint.tv_loadingSearchText.startAnimation(resultsOUT)


                        if (isReturnFlight){
                            binding.selectFlightsCard.loadingConstraint.tv_loadingDep.text = flightViewModel.ariIata
                            binding.selectFlightsCard.loadingConstraint.tv_loadingAri.text = flightViewModel.depIata


                        }else {
                            binding.selectFlightsCard.loadingConstraint.tv_loadingDep.text = flightViewModel.depIata
                            binding.selectFlightsCard.loadingConstraint.tv_loadingAri.text = flightViewModel.ariIata
                        }


                        binding.selectFlightsCard.loadingConstraint.tv_loadingFrom.visibility = View.VISIBLE
                        binding.selectFlightsCard.loadingConstraint.tv_loadingFrom.startAnimation(textIN)

                        binding.selectFlightsCard.loadingConstraint.tv_loadingDep.visibility = View.VISIBLE
                        binding.selectFlightsCard.loadingConstraint.tv_loadingDep.startAnimation(textIN)



                        binding.selectFlightsCard.loadingConstraint.iv_loadingPlane.visibility = View.VISIBLE
                        binding.selectFlightsCard.loadingConstraint.iv_loadingPlane.startAnimation(textIN)



                        binding.selectFlightsCard.loadingConstraint.tv_loadingTo.visibility = View.VISIBLE
                        binding.selectFlightsCard.loadingConstraint.tv_loadingTo.startAnimation(textIN)



                        binding.selectFlightsCard.loadingConstraint.tv_loadingAri.visibility = View.VISIBLE
                        binding.selectFlightsCard.loadingConstraint.tv_loadingAri.startAnimation(textIN)


                        //Handler().postDelayed({
                            binding.selectFlightsCard.loadingConstraint.tv_loadingSearchText.visibility = View.VISIBLE
                            binding.selectFlightsCard.loadingConstraint.tv_loadingSearchText.startAnimation(textIN)

                        //}, 800)


                        binding.selectFlightsCard.cvResultsFirst.visibility = View.GONE
                        binding.selectFlightsCard.cvResultsFirst.startAnimation(resultsOUT)

                        binding.selectFlightsCard.cvResultsFirst.rv_firstFlightResultsList.visibility = View.GONE
                        binding.selectFlightsCard.cvResultsFirst.rv_firstFlightResultsList.startAnimation(resultsOUT)
                    }
                }
            )

            flightViewModel.offers.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer {
                    if (it != null) {
                        if (isReturnFlight) {
                            val flightReturnAdapter: FlightReturnAdapter =
                                FlightReturnAdapter(it,
                                    flightViewModel.ariIata,
                                    flightViewModel.depIata,
                                    this)
                            binding.selectFlightsCard.cvResultsFirst.rv_firstFlightResultsList.adapter =
                                flightReturnAdapter
                        } else {
                            val flightSerachAdapter: FlightOfferAdapter =
                                FlightOfferAdapter(it,
                                    flightViewModel.depIata,
                                    flightViewModel.ariIata,
                                    this)
                            binding.selectFlightsCard.cvResultsFirst.rv_firstFlightResultsList.adapter =
                                flightSerachAdapter
                        }


                    }
                }
            )


            openFlight()


//            flightResultBttnSheetFragment.bttn_letsFly.setOnClickListener{
//                if (flightResultBttnSheetFragment !=null){
//                    flightResultBttnSheetFragment.dismiss()
//                }
//            }


        }
    }

    private fun updatelabel2(calender: Calendar) {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.GERMANY)
        returnDate.setText(sdf.format(calender.time))

    }

    private fun updatelabel(calender: Calendar) {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.GERMANY)
        departureDate.setText(sdf.format(calender.time))

    }

    override fun openFlight() {

        isReturnFlight = false
        flightViewModel.resetAllValues()

        val resultsIN = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.text_slide_in)




        flightViewModel.getFlights(
            binding.bigBookCard.cvBookField.tv_IATAdeparture.text.toString(),
            binding.bigBookCard.cvBookField.tv_IATAarrival.text.toString(),
            binding.bigBookCard.expandConstraint1.tv_depDateSelect.text.toString(),
            adultCounter
        )




    }

    override fun openReturnFlight(flight: FlightOffer, bookingNbr: String) {
        flightViewModel.resetAllValues()
        isReturnFlight = true

        flightViewModel.flight1 = flight

/*
        Handler().postDelayed({


        binding.selectFlightsCard.cvResultsFirst.rv_firstFlightResultsList.startAnimation(
            resultsOUT)
        binding.selectFlightsCard.cvResultsFirst.rv_firstFlightResultsList.visibility =
            View.GONE

//        binding.selectFlightsCard.loadingConstraint.tv_loadingDep.startAnimation(resultsOUT)
//        binding.selectFlightsCard.loadingConstraint.tv_loadingDep.visibility = View.GONE
//
//        binding.selectFlightsCard.loadingConstraint.tv_loadingAri.startAnimation(resultsOUT)
//        binding.selectFlightsCard.loadingConstraint.tv_loadingAri.visibility = View.GONE
        }, 3800)

 */




        flightViewModel.getFlights(
            binding.bigBookCard.cvBookField.tv_IATAarrival.text.toString(),
            binding.bigBookCard.cvBookField.tv_IATAdeparture.text.toString(),
            binding.bigBookCard.expandConstraint1.tv_arriveDateSelect.text.toString(),
            adultCounter
        )
/*
        val resultsOUT = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.slide_down)

        binding.selectFlightsCard.cvResultsFirst.startAnimation(
            resultsOUT)
        binding.selectFlightsCard.cvResultsFirst.visibility =
            View.GONE

 */

    }

    override fun openPayment(depIata: String, ariIata: String, returnFlight: FlightOffer, returnBookingNbr: String) {

        binding.payCard.cvPayCARD.payCARDConstraint.tv_departIATA.text = ariIata
        binding.payCard.cvPayCARD.payCARDConstraint.tv_departCity.text

        binding.payCard.cvPayCARD.payCARDConstraint.tv_arrivalIATA1.text = depIata
        binding.payCard.cvPayCARD.payCARDConstraint.tv_arrivalCity1.text

        binding.payCard.cvPayCARD.payCARDConstraint.tv_arrivalIATA2.text = ariIata
        binding.payCard.cvPayCARD.payCARDConstraint.tv_arrivalCity2.text

        binding.payCard.cvPayCARD.payCARDConstraint.cv_flight1.tv_flightNbr1.text
        binding.payCard.cvPayCARD.payCARDConstraint.cv_flight1.tv_adult1.text = "1 Adult"
        binding.payCard.cvPayCARD.payCARDConstraint.cv_flight1.tv_cabinClass1.text = "First Class"
        binding.payCard.cvPayCARD.payCARDConstraint.cv_flight1.tv_Date1.text = "DATE"
        binding.payCard.cvPayCARD.payCARDConstraint.cv_flight1.tv_Time1.text = "TIME"
        binding.payCard.cvPayCARD.payCARDConstraint.cv_flight1.tv_Date2.text = "DATE"
        binding.payCard.cvPayCARD.payCARDConstraint.cv_flight1.tv_Time2.text = "TIME"
        binding.payCard.cvPayCARD.payCARDConstraint.tv_price1.text = "PRICE"

        binding.payCard.cvPayCARD.payCARDConstraint.cv_flight2.tv_flightNbr2.text = returnBookingNbr
        binding.payCard.cvPayCARD.payCARDConstraint.cv_flight2.tv_adult2.text = "1 Adult"
        binding.payCard.cvPayCARD.payCARDConstraint.cv_flight2.tv_cabinClass2.text = "First Class"
        binding.payCard.cvPayCARD.payCARDConstraint.cv_flight2.tv_Date3.text = returnFlight.departureTime
        binding.payCard.cvPayCARD.payCARDConstraint.cv_flight2.tv_Time3.text = "TIME"
        binding.payCard.cvPayCARD.payCARDConstraint.cv_flight2.tv_Date4.text = returnFlight.arrivalTime
        binding.payCard.cvPayCARD.payCARDConstraint.cv_flight2.tv_Time4.text = "TIME"
        binding.payCard.cvPayCARD.payCARDConstraint.tv_price2.text = "EUR ${returnFlight.price}"



//        binding.payCard.cvPayCARD.payCARDConstraint.bttn_proceedPayment.visibility = View.GONE
        val resultsOUT = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.bttnbar_slide_out)
//        resultsOUT.duration = 2000

        val resultsIN = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.text_slide_in)
//        resultsIN.duration = 2000

        val backgroundIN = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.bttmsheet_slide_in)
//        backgroundIN.duration = 2000


        binding.selectFlightsCard.cvResultsFirst.startAnimation(resultsOUT)
        binding.selectFlightsCard.cvResultsFirst.visibility = View.GONE
        binding.selectFlightsCard.loadingConstraint.startAnimation(resultsOUT)
        binding.selectFlightsCard.loadingConstraint.visibility = View.GONE



        binding.payCard.ivCabinCrew.visibility = View.VISIBLE
        binding.payCard.ivCabinCrewCloud.visibility = View.VISIBLE

        binding.payCard.ivCabinCrew.startAnimation(resultsIN)
        binding.payCard.ivCabinCrewCloud.startAnimation(resultsIN)



        binding.payCard.cvPayCARD.visibility = View.VISIBLE
        binding.payCard.cvPayCARD.startAnimation(backgroundIN)
        binding.payCard.cvPayCARD.payCARDConstraint.iv_payFlight_backgroundBttnSheet.visibility = View.VISIBLE
        binding.payCard.cvPayCARD.payCARDConstraint.iv_payFlight_backgroundBttnSheet.startAnimation(resultsIN)




        Handler().postDelayed({
            binding.payCard.cvPayCARD.payCARDConstraint.tv_checkYourFlight.visibility = View.VISIBLE
            binding.payCard.cvPayCARD.payCARDConstraint.tv_checkYourFlight.startAnimation(resultsIN)

        binding.payCard.cvPayCARD.payCARDConstraint.tv_departIATA.visibility = View.VISIBLE
        val cityAnimator1 = ObjectAnimator.ofFloat(
            binding.payCard.cvPayCARD.payCARDConstraint.tv_departIATA,
            View.ROTATION_X,
            0f, 360f)

        binding.payCard.cvPayCARD.payCARDConstraint.tv_departCity.visibility = View.VISIBLE
        val cityAnimator2 = ObjectAnimator.ofFloat(
            binding.payCard.cvPayCARD.payCARDConstraint.tv_departCity,
            View.ROTATION_X,
            0f, 360f)

        val set = AnimatorSet()
        set.playTogether(cityAnimator1, cityAnimator2)
        set.start()



        binding.payCard.cvPayCARD.payCARDConstraint.iv_dotLine1.startAnimation(resultsIN)
        binding.payCard.cvPayCARD.payCARDConstraint.iv_dotLine1.visibility = View.VISIBLE


        binding.payCard.cvPayCARD.payCARDConstraint.tv_arrivalIATA1.visibility = View.VISIBLE
        val cityAnimator3 = ObjectAnimator.ofFloat(
            binding.payCard.cvPayCARD.payCARDConstraint.tv_arrivalIATA1,
            View.ROTATION_X,
            0f, 360f)

        binding.payCard.cvPayCARD.payCARDConstraint.tv_arrivalCity1.visibility = View.VISIBLE
        val cityAnimator4 = ObjectAnimator.ofFloat(
            binding.payCard.cvPayCARD.payCARDConstraint.tv_arrivalCity1,
            View.ROTATION_X,
            0f, 360f)

        val set2 = AnimatorSet()
        set2.playTogether(cityAnimator3, cityAnimator4)
        set2.start()





        binding.payCard.cvPayCARD.payCARDConstraint.cv_flight1.visibility = View.VISIBLE
        val flight1Rotator = ObjectAnimator.ofFloat(
            binding.payCard.cvPayCARD.payCARDConstraint.cv_flight1,
            View.ROTATION_X,
            0f, 360f)
        flight1Rotator.duration = 2000

        binding.payCard.cvPayCARD.payCARDConstraint.iv_dotPlane1.visibility = View.VISIBLE
        val plane1Animator = ObjectAnimator.ofFloat(
            binding.payCard.cvPayCARD.payCARDConstraint.iv_dotPlane1,
            View.ROTATION_X,
            0f, 360f)
        plane1Animator.duration = 2000


        val set3 = AnimatorSet()
        set3.playTogether(flight1Rotator, plane1Animator)
        set3.start()
        }, 1800)






        Handler().postDelayed({
            binding.payCard.cvPayCARD.payCARDConstraint.tv_arrivalIATA2.visibility = View.VISIBLE
            val cityAnimator5 = ObjectAnimator.ofFloat(
                binding.payCard.cvPayCARD.payCARDConstraint.tv_arrivalIATA2,
                View.ROTATION_X,
                0f, 360f)

            binding.payCard.cvPayCARD.payCARDConstraint.tv_arrivalCity2.visibility = View.VISIBLE
            val cityAnimator6 = ObjectAnimator.ofFloat(
                binding.payCard.cvPayCARD.payCARDConstraint.tv_arrivalCity2,
                View.ROTATION_X,
                0f, 360f)

            val set4 = AnimatorSet()
            set4.playTogether(cityAnimator5, cityAnimator6)
            set4.start()




            binding.payCard.cvPayCARD.payCARDConstraint.iv_dotLine2.startAnimation(resultsIN)
            binding.payCard.cvPayCARD.payCARDConstraint.iv_dotLine2.visibility = View.VISIBLE


            binding.payCard.cvPayCARD.payCARDConstraint.cv_flight2.visibility = View.VISIBLE
            val flight2Rotator = ObjectAnimator.ofFloat(
                binding.payCard.cvPayCARD.payCARDConstraint.cv_flight2,
                View.ROTATION_X,
                0f, 360f)
            flight2Rotator.duration = 2000

            binding.payCard.cvPayCARD.payCARDConstraint.iv_dotPlane2.visibility = View.VISIBLE
            val plane2Animator = ObjectAnimator.ofFloat(
                binding.payCard.cvPayCARD.payCARDConstraint.iv_dotPlane2,
                View.ROTATION_X,
                0f, 360f)
            plane2Animator.duration = 2000


            val set5 = AnimatorSet()
            set5.playTogether(flight2Rotator, plane2Animator)
            set5.start()

            Handler().postDelayed({
            binding.payCard.cvPayCARD.payCARDConstraint.bttn_proceedPayment.startAnimation(resultsIN)
            binding.payCard.cvPayCARD.payCARDConstraint.bttn_proceedPayment.visibility = View.VISIBLE

            binding.payCard.cvPayCARD.payCARDConstraint.tv_checkYourFlight.startAnimation(resultsOUT)
            binding.payCard.cvPayCARD.payCARDConstraint.tv_checkYourFlight.visibility = View.GONE

            binding.payCard.cvPayCARD.payCARDConstraint.tv_price1.startAnimation(resultsIN)
            binding.payCard.cvPayCARD.payCARDConstraint.tv_price1.visibility = View.VISIBLE
            binding.payCard.cvPayCARD.payCARDConstraint.tv_price2.startAnimation(resultsIN)
            binding.payCard.cvPayCARD.payCARDConstraint.tv_price2.visibility = View.VISIBLE

            }, 1800)

        }, 3800)









    }


}




