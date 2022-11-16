package com.example.beaverairlines.home.childFragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.amadeus.resources.TripDetail.Air
import com.example.beaverairlines.AirportViewModel
import com.example.beaverairlines.FlightResultSheetFragment
import com.example.beaverairlines.R
import com.example.beaverairlines.ViewModel
import com.example.beaverairlines.adapter.AirportAdapter
import com.example.beaverairlines.adapter.CabinClassAdapter
import com.example.beaverairlines.adapter.IataAdapter
import com.example.beaverairlines.data.model.CabinClass
import com.example.beaverairlines.data.model.CabinClassSource
import com.example.beaverairlines.databinding.FragmentBookBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firestore.admin.v1.Index
import kotlinx.android.synthetic.main.book3_card.*
import kotlinx.android.synthetic.main.book3_card.view.*
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


class BookFragment: Fragment() {

    private lateinit var binding: FragmentBookBinding

    private lateinit var departureDate: TextView
    private lateinit var returnDate: TextView
    private lateinit var adultPassengers: TextView
    private lateinit var infantPassengers: TextView
    private var adultCounter: Int = 1
    private var infantCounter: Int = 0


    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val airportViewModel: AirportViewModel by activityViewModels()
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



        val iata = flightViewModel.iata
        //val iataAdapter = IataAdapter(iata)
        val iataAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, iata)
        selectDepartureCity.setAdapter(iataAdapter)
       // binding.bigBookCard.cvBookField.tv_departCitySelect.adapter= iataAdapter
//        selectDepartureCity.setOnItemClickListener { adapterView, view, i, l -> }

        departureDate = view.findViewById(R.id.tv_depDateSelect)
        returnDate = view.findViewById(R.id.tv_arriveDateSelect)
        val calender = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year,month, dayOfMonth ->
            calender.set(Calendar.YEAR,year)
            calender.set(Calendar.MONTH, month)
            calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updatelabel(calender)
        }

        val datePicker2 = DatePickerDialog.OnDateSetListener { view, year,month, dayOfMonth ->
            calender.set(Calendar.YEAR,year)
            calender.set(Calendar.MONTH, month)
            calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updatelabel2(calender)
        }

        departureDate.setOnClickListener {
            DatePickerDialog(requireContext(), datePicker, calender.get(Calendar.YEAR),calender.get(Calendar.MONTH),
            calender.get(Calendar.DAY_OF_MONTH)).show()
        }

        returnDate.setOnClickListener {
            DatePickerDialog(requireContext(), datePicker2, calender.get(Calendar.YEAR),calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH)).show()
        }

        val term = selectDepartureCity.text.toString()
        airportViewModel.searchAirports(term)

        val term2 = selectArrivalCity.text.toString()
        airportViewModel.searchAirports(term2)
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

        binding.bigBookCard.expandConstraint2.tv_cabinSelectedText.text = "Please select a Cabin Class"

        //Hier wird der die vom ccAdapter übergebende "Cabin Class"- Unit aufgerufen:
        ccAdapter.setOnItemClickListener {
           binding.bigBookCard.expandConstraint2.tv_cabinSelectedText.text = it.title
        }


        adultPassengers = view.findViewById(R.id.tv_adultAmount)
        infantPassengers = view.findViewById(R.id.tv_infantAmount)

        adultPassengers.text = adultCounter.toString()
        infantPassengers.text = infantCounter.toString()

        binding.bigBookCard.expandConstraint3.bttn_adultPlus.setOnClickListener {
            adultCounter ++
            adultPassengers.text = adultCounter.toString()
        }

        binding.bigBookCard.expandConstraint3.bttn_adultMinus.setOnClickListener {
            adultCounter --
            if (adultCounter < 0) {adultCounter = 0}
            adultPassengers.text = adultCounter.toString()
        }

        binding.bigBookCard.expandConstraint3.bttn_infantPlus.setOnClickListener {
            infantCounter ++
            infantPassengers.text = infantCounter.toString()
        }

        binding.bigBookCard.expandConstraint3.bttn_infantMinus.setOnClickListener {
            infantCounter --
            if (infantCounter < 0) {infantCounter = 0}
            infantPassengers.text = infantCounter.toString()
        }









        val slideInRight = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.slide_in_right)


        binding.bigBookCard.ibArrow1.setOnClickListener {
            if (expandConstraint1.visibility == View.GONE){
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
            if (expandConstraint2.visibility == View.GONE){
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
            if (expandConstraint3.visibility == View.GONE){
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
            if (expandConstraint4.visibility == View.GONE){
                TransitionManager.beginDelayedTransition(expandCard, AutoTransition())
                expandConstraint4.visibility = View.VISIBLE
                ib_arrow4.animate().setDuration(2).rotationBy(108f).start()
                ib_arrow4.visibility = View.GONE
                iv_redlineIndicator4.visibility = View.VISIBLE
                iv_redlineIndicator4.startAnimation(slideInRight)
                iv_planeIndicator.startAnimation(slideInRight)
                iv_planeIndicator.translationX = iv_planeIndicator.translationX + 230
            } else {
                TransitionManager.beginDelayedTransition(expandCard, AutoTransition())
                expandConstraint4.visibility = View.GONE
                ib_arrow4.visibility = View.GONE
                ib_arrow4.animate().setDuration(2).rotationBy(108f).start()

            }
        }





        binding.bigBookCard.bttnSearchFlights.setOnClickListener {

            flightViewModel.getFlights(
                binding.bigBookCard.cvBookField.tv_departCitySelect.text.toString(),
                binding.bigBookCard.cvBookField.tv_arriveCitySelect.text.toString(),
                binding.bigBookCard.expandConstraint1.tv_depDateSelect.text.toString(),
                adultCounter
            )

            flightViewModel.started.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer {
                    if (it){
                        val flightResultBttnSheetFragment = FlightResultSheetFragment()
                        flightResultBttnSheetFragment.show((activity as AppCompatActivity).supportFragmentManager,"bttmSheet")
                    }
                }
            )
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


}



