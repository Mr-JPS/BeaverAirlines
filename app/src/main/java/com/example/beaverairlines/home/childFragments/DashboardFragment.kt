package com.example.beaverairlines.home.childFragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.beaverairlines.AuthViewModel
import com.example.beaverairlines.R
import com.example.beaverairlines.ViewModel
import com.example.beaverairlines.adapter.AdvertisingAdapter
import com.example.beaverairlines.adapter.DestinationAdapter
import com.example.beaverairlines.adapter.IataArrayAdapter
import com.example.beaverairlines.data.Iata
import com.example.beaverairlines.data.model.AdSource
import com.example.beaverairlines.data.model.DestinationSource
import com.example.beaverairlines.databinding.FragmentDashboardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import java.util.*
import kotlin.collections.ArrayList


class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private val flightViewModel: ViewModel by activityViewModels()
    private val viewModel: AuthViewModel by activityViewModels()

    private lateinit var departure_city: TextView
    private lateinit var arrival_city: TextView
    private lateinit var dBtempIataArrayList: ArrayList<Iata>

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentDashboardBinding.inflate(inflater)
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val slideInRight = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.slide_in_right)

        val slideOutRight = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.slide_out_right)


        getUserName()

        val dbMemberCard = binding.cvHello.DBMileHighCard
        val dbBookCard = binding.cvBook
        val dbArrowBttn = binding.cvHello.DBBttnArrow
        val dashScrollview = binding.DashScrollView
        val plane = binding.cvBook.ivPlane2

//        dbArrowBttn.setOnClickListener {
//
//            arrowAnimator = ObjectAnimator.ofInt(dbBookCard, View.SCROLL_Y, 0, 250)
//
//        }

//        var wasPlanemoved = false
//
//        if (!wasPlanemoved){
//        dashScrollview.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//
//             plane.startAnimation(slideInRight)
//         }
//        }
//
//         wasPlanemoved = true


        dbMemberCard.setOnClickListener {
            flightViewModel.clubCardClicked.value = true
        }


        binding.cvBook.ivPlane2.startAnimation(slideInRight)


        val dBselectDepartureCity: AutoCompleteTextView = binding.cvBook.tvDepartCitySelect
        val dBselectArrivalCity: AutoCompleteTextView = binding.cvBook.tvArriveCitySelect
        val dBarrivalIata: TextView = binding.cvBook.tvIATAarrival
        val dBdepartureIata: TextView = binding.cvBook.tvIATAdeparture
        val dBsearchFlightBttn = binding.cvBook.bttnSearch


        dBtempIataArrayList = arrayListOf<Iata>()

        val dBiata = flightViewModel.iata

        context?.let { ctx ->
            val iataArrayAdapter = IataArrayAdapter(ctx, R.layout.iata_item, dBiata)
            dBselectArrivalCity.setAdapter(iataArrayAdapter)
            dBselectArrivalCity.setOnItemClickListener { parent, _, position, _ ->
                val iata = iataArrayAdapter.getItem(position) as Iata?
                dBselectArrivalCity.setText(iata?.name)
                flightViewModel.ariCity2 = (iata?.name.toString())
                dBarrivalIata.setText(iata?.iata)
                flightViewModel.ariIata2 = (iata?.iata.toString())
            }

        }

        context?.let { ctx ->
            val iataArrayAdapter2 = IataArrayAdapter(ctx, R.layout.iata_item, dBiata)
            dBselectDepartureCity.setAdapter(iataArrayAdapter2)
            dBselectDepartureCity.setOnItemClickListener { parent, _, position, _ ->
                val iata2 = iataArrayAdapter2.getItem(position) as Iata?
                dBselectDepartureCity.setText(iata2?.name)
                flightViewModel.depCity2 = (iata2?.name.toString())
                dBdepartureIata.setText(iata2?.iata)
                flightViewModel.depIata2 = (iata2?.iata.toString())
            }

        }

        dBsearchFlightBttn.setOnClickListener {
            flightViewModel.dBFlightSearchClicked.value = true

        }


        val destinations = DestinationSource().loadDestination()
        binding.destinationRecycler.adapter = DestinationAdapter(destinations)
        binding.destinationRecycler.setHasFixedSize(true)
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.destinationRecycler)


        val advertising1 = AdSource().loadAd()
        val ad1Recycler = binding.cvAdvertising.adRecycler
        val ad1TimeInSec : Long = 4000
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        ad1Recycler.setLayoutManager(linearLayoutManager)

        val ad1Adapter = AdvertisingAdapter(advertising1)
        ad1Recycler.adapter = ad1Adapter
        ad1Recycler.setHasFixedSize(true)
        val snapHelper2: SnapHelper = PagerSnapHelper()
        snapHelper2.attachToRecyclerView(ad1Recycler)

        val timer: Timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() < ad1Adapter.getItemCount() - 1) {
                    linearLayoutManager.smoothScrollToPosition(ad1Recycler,
                        RecyclerView.State(),
                        linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1)
                } else if (linearLayoutManager.findLastCompletelyVisibleItemPosition() === ad1Adapter.getItemCount() - 1) {
                    linearLayoutManager.smoothScrollToPosition(ad1Recycler, RecyclerView.State(), 0)
                }
            }
        }, 0, ad1TimeInSec)

        val checkInCard = binding.cvCheckin.CIMainCard
        checkInCard.setOnClickListener {
            flightViewModel.ciCardClicked.value = true
        }


//        val snapHelper: SnapHelper = PagerSnapHelper()
//        snapHelper.attachToRecyclerView(binding.destinationRecycler)

    }


    fun getUserName() {
        db.collection("user").document(auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener {
                val userName = it.getString("firstName")

                binding.cvHello.tvUserName.text = userName
            }
    }


}



