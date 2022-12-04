package com.example.beaverairlines.home.childFragments

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.widget.NestedScrollView
import androidx.dynamicanimation.animation.DynamicAnimation.SCROLL_Y
import androidx.fragment.app.activityViewModels
import com.example.beaverairlines.AuthViewModel
import com.example.beaverairlines.R
import com.example.beaverairlines.ViewModel
import com.example.beaverairlines.adapter.AdvertisingAdapter
import com.example.beaverairlines.adapter.DestinationAdapter
import com.example.beaverairlines.adapter.IataArrayAdapter
import com.example.beaverairlines.data.FlightOffer
import com.example.beaverairlines.data.Iata
import com.example.beaverairlines.data.model.AdSource
import com.example.beaverairlines.data.model.DestinationSource
import com.example.beaverairlines.databinding.FragmentDashboardBinding
import com.example.beaverairlines.utils.BookInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


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

        val advertisings = AdSource().loadAd()
        binding.adRecycler.adapter = AdvertisingAdapter(advertisings)
        binding.adRecycler.setHasFixedSize(true)


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



