package com.example.beaverairlines.home

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView

import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.beaverairlines.*
import com.example.beaverairlines.databinding.FragmentNavControllerBinding
import com.example.beaverairlines.home.childFragments.BookFragment
import com.example.beaverairlines.home.childFragments.CheckinFragment
import com.example.beaverairlines.home.childFragments.DashboardFragment
import com.example.beaverairlines.home.childFragments.TripsFragment
import com.example.beaverairlines.utils.SwipeControlTouchListener
import com.example.beaverairlines.utils.SwipeDirection
import com.example.beaverairlines.BookingViewModel
import com.example.beaverairlines.ViewModel
import kotlinx.android.synthetic.main.fragment_nav_controller.*

//FRAGMENT FOR HANDLING LOGIC OF VIEW PAGER 2

class NavControllerFragment: Fragment() {

    private var _binding: FragmentNavControllerBinding? = null
    private val binding get() = _binding!!

    private val swipeControlTouchListener by lazy {
        SwipeControlTouchListener()
    }

    private val viewModel: ViewModel by activityViewModels()
    private val bookingViewModel: BookingViewModel by activityViewModels()
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


        //OBSERVER WATCHING CHANGES IN PAYMENT PROCEDURE (FOR SWITCHING VIEW PAGER FRAMES):
        viewModel.paymentCompleted.observe(
            viewLifecycleOwner
        ){
            if (it){
                binding.pager.currentItem = 3
            }
        }


        //OBSERVER WATCHING IF CLUB CARD IN DASHBAORD WAS CLICKED (FOR SWITCHING VIEW PAGER FRAMES):
        viewModel.clubCardClicked.observe(
            viewLifecycleOwner
        ){
            if (it){
                binding.pager.currentItem = 3
            }
        }


        //OBSERVER WATCHING IF QUICK BOOKING IN DASHBAORD WAS CLICKED (FOR SWITCHING VIEW PAGER FRAMES):
        viewModel.dBFlightSearchClicked.observe(
            viewLifecycleOwner
        ){
            if (it){
                binding.pager.currentItem = 1
            }
        }


        //OBSERVER WATCHING IF QUICK CHECKIN IN DASHBAORD WAS CLICKED (FOR SWITCHING VIEW PAGER FRAMES):
        viewModel.ciCardClicked.observe(
            viewLifecycleOwner
        ){
            if (it){
                binding.pager.currentItem = 2
            }
        }



        val slideInLeft = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.plane_slide_in_right)


        binding.menuBttn.alpha = 0.5F
        binding.menuBttn.setOnClickListener {

            //METHOD FOR NAV BAR BEHAVIOUR
            onBttnClicked()
        }


        //CODE FOR CONTROLLING BEHAVIOUR OF VIEW PAGER:
        val adapter = ViewPagerAdapter(this)
        binding.pager.adapter = adapter

        val viewPager2recyclerView = binding.pager[0] as? RecyclerView
        if(viewPager2recyclerView != null){
            swipeControlTouchListener.setSwipeDirection(SwipeDirection.ALL)
            viewPager2recyclerView.addOnItemTouchListener(swipeControlTouchListener)
        }


        //CODE FOR SHOWING SWIPE GESTURES IN NAV BAR:
        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bubbleTabBar.setSelected(position)
                if(position == 2)
                    bookingViewModel.getNextCheckin()
            }
        })



        //CODE FOR BEING DIRECTED TO ACCORDING VIEW PAGE BY TAPPING ON NAV BAR:
        binding.bubbleTabBar.addBubbleListener { id ->
            when(id){
                R.id.dashboard_fragment -> binding.pager.currentItem = 0
                R.id.book_fragment -> binding.pager.currentItem = 1
                R.id.checkin_fragment -> binding.pager.currentItem = 2
                R.id.trips_fragment -> binding.pager.currentItem = 3
            }
        }
    }


    //METHOD FOR CHANGING VISIBILITY OF NAV BAR BTTN:
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


    //METHOD FOR CHANGING VISIBILITY AND ANIMATION OF NAV BAR:
    private fun setVisibility(clicked : Boolean) {
        if (animator == null){
            animator = createAnimator()
        }

        if (!clicked) {
            animator!!.reverse()
            binding.menuBttn.alpha = 0.5F

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

        } else {
            animator!!.start()
            binding.menuBttn.alpha = 1F

            val slideIn = AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.bttn_slide_in)

            binding.navBackGround.startAnimation(slideIn)
            binding.bubbleTabBar.startAnimation(slideIn)
            binding.navBackGround.visibility = View.VISIBLE
            binding.bubbleTabBar.visibility = View.VISIBLE
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    //VIEW PAGER ADAPTER
    class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = 4

        //ORDER OF FRAGMENTS HOSTED BY VIEW PAGER:
        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> DashboardFragment()
                1 -> BookFragment()
                2 -> CheckinFragment()
                else -> TripsFragment()
            }
        }
    }


    //METHOD FOR ANIMATION
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