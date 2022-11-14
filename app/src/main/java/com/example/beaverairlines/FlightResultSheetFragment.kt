package com.example.beaverairlines


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.beaverairlines.databinding.FragmentFlightresultsheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FlightResultSheetFragment: BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFlightresultsheetBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_flightresultsheet,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //hier die func für die flugergebnisse


        Toast.makeText(context,"bttm sheet open", Toast.LENGTH_SHORT).show()
    }
}