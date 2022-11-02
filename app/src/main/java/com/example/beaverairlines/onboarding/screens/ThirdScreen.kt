package com.example.beaverairlines.onboarding.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.example.beaverairlines.R
import kotlinx.android.synthetic.main.fragment_third_screen.view.*


class ThirdScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val view = inflater.inflate(R.layout.fragment_third_screen, container, false)

        val slideDown = AnimationUtils.loadAnimation(
            requireContext(),
            androidx.appcompat.R.anim.abc_slide_in_top)

        val slideInRight = AnimationUtils.loadAnimation(
            requireContext(),
            com.google.android.material.R.anim.abc_fade_in)

//        view.OB_pic3.animate().apply {
//            duration = 1100
//            rotationXBy(360f)
//        }.start()

        view.OB_pic3.startAnimation(slideDown)
        view.OB_bttn3.startAnimation(slideInRight)



        view.OB_bttn3.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_authFragment)
            onBoardingFinished()
        }

        return view
    }

    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

}