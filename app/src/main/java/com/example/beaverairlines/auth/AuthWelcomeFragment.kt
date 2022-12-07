package com.example.beaverairlines.auth

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.beaverairlines.R
import com.example.beaverairlines.databinding.FragmentAuthBinding

//THIS FRAGMENT IS PART OF THE LOGIN / SIGN IN PROCEDURE:

class AuthWelcomeFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding



    fun Activity.hideKeyboard(view: View) {
        hideKeyboard(currentFocus ?: View(this))
    }



    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val slideInLeft = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.slide_in_left_slow)

        val slideInRight = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.slide_in_right)

        val fadeIn = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.fade_in)


        binding.authCardLayoutCl.visibility = View.GONE
        binding.authBackground2Iv.startAnimation(slideInRight)
        binding.authWelcomeTv.startAnimation(slideInLeft)
        binding.authAboardTv.startAnimation(slideInLeft)

        //A DELAY FOR SHOWING THE ITEM AT TIME:
        Handler().postDelayed(Runnable {
            binding.authCardLayoutCl.visibility = View.VISIBLE
            binding.authCardLayoutCl.startAnimation(fadeIn)
        }, 1000)



        binding.authSignupBttn.setOnClickListener {

            val transition = FragmentNavigatorExtras(binding.authCardLayoutCl to "signUp_card")
            findNavController().navigate(AuthWelcomeFragmentDirections.actionAuthFragmentToSigninFragment(false),
                transition
            )
        }



        binding.authLoginBttn.setOnClickListener {

            val transition = FragmentNavigatorExtras(binding.authCardLayoutCl to "login_card")
            findNavController().navigate(AuthWelcomeFragmentDirections.actionAuthFragmentToSigninFragment(true),
                transition
            )
        }


    }
}