package com.example.beaverairlines

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_splash.view.*


class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        view.splash_image.animate().apply {
            duration = 1100
            rotationXBy(360f)
        }.start()

       Handler().postDelayed(
           {
               if (onBoardingFinished()) {
                   findNavController().navigate(R.id.action_splashFragment_to_authFragment)
               } else {
                   findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
               }
           },
           1800,
       )


    return view
    }

    private fun onBoardingFinished(): Boolean{
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }
}