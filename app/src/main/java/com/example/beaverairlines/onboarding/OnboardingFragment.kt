package com.example.beaverairlines.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.beaverairlines.R
import com.example.beaverairlines.adapter.OnboardingAdapter
import com.example.beaverairlines.onboarding.screens.Onboarding1
import com.example.beaverairlines.onboarding.screens.Onboarding2
import com.example.beaverairlines.onboarding.screens.Onboarding3
import kotlinx.android.synthetic.main.fragment_onboarding.view.*

//FRAGMENT FOR ONBOARDING LOGIC (VIA VIEW PAGER)

class OnboardingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val view = inflater.inflate(R.layout.fragment_onboarding, container, false)

        val fragmentList = arrayListOf<Fragment>(
            Onboarding1(),
            Onboarding2(),
            Onboarding3()
        )

        val adapter = OnboardingAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )


        view.onboarding_viewPager.adapter = adapter

        return view
    }

}