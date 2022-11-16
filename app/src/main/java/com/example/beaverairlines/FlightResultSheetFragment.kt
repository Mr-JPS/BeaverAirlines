package com.example.beaverairlines


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.beaverairlines.adapter.FlightOfferAdapter
import com.example.beaverairlines.databinding.FragmentFlightresultsheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FlightResultSheetFragment: BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFlightresultsheetBinding

    private val flightViewModel: ViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFlightresultsheetBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //hier die func für die flugergebnisse
        flightViewModel.resetAllValues()
        flightViewModel.offers.observe(
            viewLifecycleOwner,
            Observer {
                if (it != null){
                    val flightSerachAdapter: FlightOfferAdapter = FlightOfferAdapter(it)
                    binding.rvFlightResultsList.adapter = flightSerachAdapter
                }
            }
        )
        Toast.makeText(context,"bttm sheet open", Toast.LENGTH_SHORT).show()
    }
}