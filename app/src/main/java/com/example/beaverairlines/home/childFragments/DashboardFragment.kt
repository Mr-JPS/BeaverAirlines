package com.example.beaverairlines.home.childFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.beaverairlines.R
import com.example.beaverairlines.adapter.DestinationAdapter
import com.example.beaverairlines.data.model.DataSource
import com.example.beaverairlines.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    private lateinit var departure_city : TextView
    private lateinit var arrival_city : TextView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentDashboardBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val destinations = DataSource().loadDestination()

        binding.destinationRecycler.adapter = DestinationAdapter(destinations)
        binding.destinationRecycler.setHasFixedSize(true)

//        val snapHelper: SnapHelper = PagerSnapHelper()
//        snapHelper.attachToRecyclerView(binding.destinationRecycler)
    }


}