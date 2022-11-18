package com.example.beaverairlines.home.childFragments

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.Path
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ListAdapter
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.window.Dialog
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
import com.example.beaverairlines.adapter.IataArrayAdapter
import com.example.beaverairlines.data.Iata
import com.example.beaverairlines.data.model.CabinClass
import com.example.beaverairlines.data.model.CabinClassSource
import com.example.beaverairlines.databinding.FragmentBookBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firestore.admin.v1.Index
import kotlinx.android.synthetic.main.book3_card.*
import kotlinx.android.synthetic.main.book3_card.view.*
import kotlinx.android.synthetic.main.diaolog_progress.view.*
import kotlinx.android.synthetic.main.fragment_book.view.*
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class BookFragment: Fragment() {

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
        val arrivalIata: TextView = view.findViewById(R.id.tv_IATAarrival)
        val departureIata: TextView = view.findViewById(R.id.tv_IATAdeparture)


        tempIataArrayList = arrayListOf<Iata>()


        val iata = flightViewModel.iata

        tempIataArrayList.addAll(iata)

        //val iataAdapter = IataAdapter(iata)
        //val iataAdapter2 = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, iata)
        //selectArrivalCity.setAdapter(iataAdapter2)

        context?.let { ctx ->
            val iataArrayAdapter2 = IataArrayAdapter(ctx,R.layout.iata_item, iata)
            selectDepartureCity.setAdapter(iataArrayAdapter2)
            selectDepartureCity.setOnItemClickListener { parent, _, position, _ ->
                val iata = iataArrayAdapter2.getItem(position) as Iata?
                selectDepartureCity.setText(iata?.name)
                departureIata.setText(iata?.iata)
            }

        }

        context?.let { ctx ->
            val iataArrayAdapter = IataArrayAdapter(ctx,R.layout.iata_item, iata)
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

            flightViewModel.depIata =  binding.bigBookCard.cvBookField.tv_IATAdeparture.text.toString()
            flightViewModel.ariIata =  binding.bigBookCard.cvBookField.tv_IATAarrival.text.toString()

            flightViewModel.getFlights(
                binding.bigBookCard.cvBookField.tv_IATAdeparture.text.toString(),
                binding.bigBookCard.cvBookField.tv_IATAarrival.text.toString(),
                binding.bigBookCard.expandConstraint1.tv_depDateSelect.text.toString(),
                adultCounter
            )


            binding.constraintBigBookCard.bttn2.visibility = View.VISIBLE
            val scaleBttnX = PropertyValuesHolder.ofFloat(View.SCALE_X, 10f)
            val scaleBttnY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 13f)
            val animatorBttn = ObjectAnimator.ofPropertyValuesHolder(binding.constraintBigBookCard.bttn2, scaleBttnX, scaleBttnY)
            animatorBttn.duration = 300
            //animatorS.repeatCount = 1
            //animatorS.repeatMode = ObjectAnimator.REVERSE
            //animatorS.interpolator = BounceInterpolator()
            animatorBttn.start()


/*
            val layoutOut = AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.bttn_slide_out)
            binding.flightInputLayout.startAnimation(layoutOut)
            binding.flightInputLayout.visibility = View.GONE

 */

            Handler().postDelayed({

                val layoutIn = AnimationUtils.loadAnimation(
                    requireContext(),
                    R.anim.bttn_slide_in)
                binding.loadingConstraint.visibility = View.VISIBLE
                binding.loadingConstraint.startAnimation(layoutIn)

                val earthIn = AnimationUtils.loadAnimation(
                    requireContext(),
                    R.anim.bttn_slide_in)
                binding.pbEarth.visibility = View.VISIBLE

                val animatorY = ObjectAnimator.ofFloat(binding.pbEarth, View.TRANSLATION_Y, -950f)
                animatorY.duration = 2000

                val animatorX = ObjectAnimator.ofFloat(binding.pbEarth, View.TRANSLATION_X, -300f)
                animatorX.duration = 1000
                animatorX.repeatCount = 1
                animatorX.repeatMode = ObjectAnimator.REVERSE

                var toAlpha = 1f
                if (binding.pbEarth.alpha == 0f) {
                    toAlpha = 1f
                }

                val animatorF = ObjectAnimator.ofFloat(binding.pbEarth, View.ALPHA, toAlpha)
                animatorF.duration = 500
                animatorF.start()


                val scaleSX = PropertyValuesHolder.ofFloat(View.SCALE_X, 10f)
                val scaleSY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 10f)
                val animatorS =
                    ObjectAnimator.ofPropertyValuesHolder(binding.pbEarth, scaleSX, scaleSY)
                animatorS.duration = 1500
                animatorS.start()


                val set = AnimatorSet()
                set.playTogether(animatorX, animatorY, animatorF, animatorS)
                set.start()

            },800
            )



            //binding.pbEarth.startAnimation(earthIn)
/*
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val path = Path().apply {
                    arcTo(0f, 0f, 1000f, 1000f, 270f, -180f, true)
                }
                val animator = ObjectAnimator.ofFloat(binding.pbEarth, View.X, View.Y, path).apply {
                    duration = 2000
                    start()
                }
            } else {
                // Create animator without using curved path
            }

 */

            val textIN = AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.bttn_slide_in)
            binding.tvLoadingDep.text = binding.bigBookCard.cvBookField.tv_IATAdeparture.text.toString()
            binding.tvLoadingDep.visibility = View.VISIBLE
            binding.tvLoadingDep.startAnimation(textIN)

            binding.tvLoadingFrom.visibility = View.VISIBLE
            binding.tvLoadingFrom.startAnimation(textIN)

            binding.ivLoadingPlane.visibility = View.VISIBLE
            binding.ivLoadingPlane.startAnimation(textIN)

            binding.tvLoadingAri.text = binding.bigBookCard.cvBookField.tv_IATAarrival.text.toString()
            binding.tvLoadingAri.visibility = View.VISIBLE
            binding.tvLoadingAri.startAnimation(textIN)

            binding.tvLoadingTo.visibility = View.VISIBLE
            binding.tvLoadingTo.startAnimation(textIN)


            binding.ivLoadingBeaver.visibility = View.VISIBLE
            binding.ivLoadingBeaver.animate().apply {
                duration = 1100
                rotationXBy(360f)
            }.start()




            Handler().postDelayed({
                flightViewModel.status.observe(
                    viewLifecycleOwner,
                    androidx.lifecycle.Observer {
                        if (it) {
                            val beaverOut = AnimationUtils.loadAnimation(
                                requireContext(),
                                R.anim.bttn_slide_out)
                            binding.ivLoadingBeaver.startAnimation(beaverOut)
                            binding.ivLoadingBeaver.visibility = View.GONE
                        }
                    }
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


            },
                3800
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



