package com.example.beaverairlines


import android.R
import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
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
import kotlinx.android.synthetic.main.fragment_splash.view.*


class FlightResultSheetFragment: BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFlightresultsheetBinding

    private val flightViewModel: ViewModel by activityViewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bottomSheet = (requireView().parent as View)
        bottomSheet.backgroundTintMode = PorterDuff.Mode.CLEAR
        bottomSheet.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
    }

    /*
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_FRAME, R.style.BottomSheetDialog)
        return super.onCreateDialog(savedInstanceState)
    }

     */


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFlightresultsheetBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        flightViewModel.status.observe(
            viewLifecycleOwner,
            Observer {
                binding.ivProgressBar.visibility = View.VISIBLE
                binding.ivProgressBar.animate().apply {
                    duration = 1100
                    rotationXBy(360f)
                }.start()
                if (it){
                    binding.ivProgressBar.visibility = View.GONE
                }
            }
        )


        //hier die func für die flugergebnisse
        flightViewModel.resetAllValues()
        flightViewModel.offers.observe(
            viewLifecycleOwner,
            Observer {
                if (it != null){
                    val flightSerachAdapter: FlightOfferAdapter = FlightOfferAdapter(it, flightViewModel.depIata,  flightViewModel.ariIata)
                    binding.rvFlightResultsList.adapter = flightSerachAdapter
                }
            }
        )
        //Toast.makeText(context,"bttm sheet open", Toast.LENGTH_SHORT).show()



    }
}