package com.example.beaverairlines.home.childFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.beaverairlines.AuthViewModel
import com.example.beaverairlines.BookingViewModel
import com.example.beaverairlines.R
import com.example.beaverairlines.ViewModel
import com.example.beaverairlines.databinding.FragmentCheckinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class CheckinFragment : Fragment() {

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

        val view = inflater.inflate(R.layout.fragment_checkin, container, false)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val client: DuffelApiClient = ("duffel_test_VBLYDE4AS2Cg7SVBdEYFvrahFvhFqQo1HTygIv7FDje")
    }

}