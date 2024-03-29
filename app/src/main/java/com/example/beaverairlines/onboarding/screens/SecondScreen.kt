package com.example.beaverairlines.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.viewpager2.widget.ViewPager2
import com.example.beaverairlines.R
import kotlinx.android.synthetic.main.fragment_second_screen.view.*


class SecondScreen : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val view = inflater.inflate(R.layout.fragment_second_screen, container, false)

        val slideInLeft = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.slide_in_left)

        val slideInRight = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.slide_in_right)

        view.OB_pic2.startAnimation(slideInLeft)
        view.OB_bttn2.startAnimation(slideInRight)


        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        view.OB_bttn2.setOnClickListener {
            viewPager?.currentItem = 2
        }

        return view
    }


}