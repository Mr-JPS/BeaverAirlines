package com.example.beaverairlines.onboarding.screens

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.example.beaverairlines.R
import kotlinx.android.synthetic.main.fragment_third_screen.view.*

//FRAGMENT FOR ONBOARDING (VIA VIEW PAGER)

class Onboarding3 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_third_screen, container, false)
        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val slideDown = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.slide_up)

        val slideInRight = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.slide_in_right)

        val slideUP = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.slide_down)

        val fadeOut = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.fade_out_fast)

        val bttnRotator = ObjectAnimator.ofFloat(
            view.OB_bttn_start,
            View.ROTATION_Y,
            360f)
        bttnRotator.duration = 1000



        Handler().postDelayed({
        view.OB_pic3.startAnimation(slideUP)
        },800)



        Handler().postDelayed({
        view.OB_bttn_start.visibility = View.VISIBLE
//      view.OB_bttn_start.startAnimation(slideDown)
        bttnRotator.start()
        },2000)



        view.OB_bttn_start.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_authFragment)
            onBoardingFinished()
        }
    }


    //METHOD TO RECOGNIZE IF ONBOARDING WAS FINISHED:
    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

}