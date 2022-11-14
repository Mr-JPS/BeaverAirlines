package com.example.beaverairlines.home

import android.animation.ValueAnimator
import android.os.Bundle
import android.transition.Transition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.beaverairlines.AuthViewModel
import com.example.beaverairlines.R
import com.example.beaverairlines.data.User
import com.example.beaverairlines.databinding.FragmentNavControllerBinding
import com.example.beaverairlines.home.childFragments.BookFragment
import com.example.beaverairlines.home.childFragments.CheckinFragment
import com.example.beaverairlines.home.childFragments.DashboardFragment
import com.example.beaverairlines.home.childFragments.TripsFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_nav_controller.*


class NavControllerFragment: Fragment() {

    private var _binding: FragmentNavControllerBinding? = null
    private val binding get() = _binding!!



    private val viewModel: AuthViewModel by activityViewModels()
   // private lateinit var transition : Transition
    private var isBttnClicked = true

    var animator : ValueAnimator? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNavControllerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val slideInLeft = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.plane_slide_in_right)

      


        binding.menuBttn.setOnClickListener {

            onBttnClicked()

        }


        val adapter = ViewPagerAdapter(this)
        binding.pager.adapter = adapter

        // page changes vom viewpager durch swipen auch in der bubble bar anzeigen
        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bubbleTabBar.setSelected(position)
            }
        })

        // beim anklicken in der bubble bar page changes beim viewpager hervorrufen
        binding.bubbleTabBar.addBubbleListener { id ->
            when(id){
                R.id.dashboard_fragment -> binding.pager.currentItem = 0
                R.id.book_fragment -> binding.pager.currentItem = 1
                R.id.checkin_fragment -> binding.pager.currentItem = 2
                R.id.trips_fragment -> binding.pager.currentItem = 3
            }
        }
    }

    private fun onBttnClicked() {

      setVisibility(isBttnClicked)
      //setAnimation(isBttnClicked)
        isBttnClicked = !isBttnClicked
    }
/*
    private fun setAnimation(clicked : Boolean) {
        val slideInLeft = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.slide_in_left)

        val slideInRight = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.slide_in_right)

        val flipIn = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.flip_in)

        val flipOut = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.flip_out)

        transition = TransitionInflater.from(requireActivity()).inflateTransition(R.transition.flip_in_trans)

        if (!clicked) {
            binding.cardView.animate().apply {
                duration = 1000
                rotationXBy(360f)
            }.start()

            //binding.beaverBttn.startAnimation(flipIn)
        } else {
            binding.cardView.animate().apply {
            duration = 1000
            rotationXBy(180f)
        }.start()

            //binding.beaverBttn.startAnimation(flipOut)
        }
    }

 */



    private fun setVisibility(clicked : Boolean) {
        if (animator == null){
            animator = createAnimator()
        }

        if (!clicked) {
            animator!!.start()

            val slideIn = AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.bttn_slide_in)

            binding.navBackGround.startAnimation(slideIn)
            binding.bubbleTabBar.startAnimation(slideIn)
            binding.navBackGround.visibility = View.VISIBLE
            binding.bubbleTabBar.visibility = View.VISIBLE


        } else {
            animator!!.reverse()

            val slideOut = AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.bttn_slide_out)

            val fastSlideOut = AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.bttnbar_slide_out)

            binding.navBackGround.startAnimation(fastSlideOut)
            binding.bubbleTabBar.startAnimation(slideOut)
            binding.navBackGround.visibility = View.INVISIBLE
            binding.bubbleTabBar.visibility = View.INVISIBLE


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = 4

        // fragmente den positionen/pages zuordnen
        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> DashboardFragment()
                1 -> BookFragment()
                2 -> CheckinFragment()
                else -> TripsFragment()
            }
        }
    }

    private fun createAnimator() : ValueAnimator {
        val initSize = menu_tv.measuredWidth
        val animator : ValueAnimator = ValueAnimator.ofInt(initSize, 0)
        animator.duration = 250

        animator.addUpdateListener { animation ->
            val value : Int = animation.animatedValue as Int
            val layoutParams: ViewGroup.LayoutParams = menu_tv.layoutParams
            layoutParams.width = value
            menu_tv.requestLayout()
        }

        return animator
    }


}