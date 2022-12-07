package com.example.beaverairlines.utils

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.example.beaverairlines.utils.SwipeDirection

//CODE FOR SWIPE BEHAVIOUR OF VIEW PAGER 2:

class SwipeControlTouchListener: RecyclerView.OnItemTouchListener  {
    private var initialXValue = 0f
    private var direction: SwipeDirection = SwipeDirection.ALL

    fun setSwipeDirection(direction: SwipeDirection) {
        this.direction = direction
    }


    override fun onInterceptTouchEvent(rv: RecyclerView, event: MotionEvent): Boolean {
        return !isSwipeAllowed(event)
    }


    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}


    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}


    private fun isSwipeAllowed(event: MotionEvent): Boolean {
        if (direction === SwipeDirection.ALL)
            return true

        if (direction == SwipeDirection.NONE) // NO SWIPING AT ALL
            return false

        if (event.action == MotionEvent.ACTION_DOWN) {
            initialXValue = event.x
            return true
        }


        if (event.action == MotionEvent.ACTION_MOVE) {
            try {
                val diffX: Float = event.x - initialXValue
                if (diffX > 0 && direction == SwipeDirection.RIGHT) {
                    // SWIPE FROM LEFT TO RIGHT
                    return false
                } else if (diffX < 0 && direction == SwipeDirection.LEFT) {
                    //SWIPE FROM RIGHT TO LEFT
                    return false
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
        return true
    }

}