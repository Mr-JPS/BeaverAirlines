package com.example.beaverairlines.home.childFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.beaverairlines.AuthViewModel
import com.example.beaverairlines.adapter.AdvertisingAdapter
import com.example.beaverairlines.adapter.DestinationAdapter
import com.example.beaverairlines.data.model.AdSource
import com.example.beaverairlines.data.model.DestinationSource
import com.example.beaverairlines.databinding.FragmentDashboardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    private val viewModel: AuthViewModel by activityViewModels()

    private lateinit var departure_city: TextView
    private lateinit var arrival_city: TextView

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentDashboardBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding.helloCard.tvUserName.setText("luca")

        getCurrentUserName()

        val destinations = DestinationSource().loadDestination()

        binding.destinationRecycler.adapter = DestinationAdapter(destinations)
        binding.destinationRecycler.setHasFixedSize(true)

        val advertisings = AdSource().loadAd()
        binding.adRecycler.adapter = AdvertisingAdapter(advertisings)
        binding.adRecycler.setHasFixedSize(true)


//        val snapHelper: SnapHelper = PagerSnapHelper()
//        snapHelper.attachToRecyclerView(binding.destinationRecycler)
    }

    fun getCurrentUserName() {
        db.collection("user").document(auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener {
                val userName = it.getString("fullName")

                binding.helloCard.tvUserName.text = userName
            }
    }
}

