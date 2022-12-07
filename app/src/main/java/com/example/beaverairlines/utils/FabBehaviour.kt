package com.example.beaverairlines.utils

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.NonNull
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

//CODE FOR BTTN BEHAVIOUR LOGIC:
//UNUSED AT THIS TIME

class FabBehaviour : CoordinatorLayout.Behavior<FloatingActionButton?> {
    var mHandler: Handler? = null

    constructor(context: Context?, attrs: AttributeSet?) : super() {}
    constructor() : super() {}

    fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout?,
        child: FloatingActionButton,
        target: View?,
        type: Int
    ) {
        if (coordinatorLayout != null) {
            if (target != null) {
                super.onStopNestedScroll(coordinatorLayout, child, target, type)
            }
        }
        if (mHandler == null) mHandler = Handler()
        mHandler!!.postDelayed(Runnable {
            child.animate().translationY(0F).setInterpolator(LinearInterpolator()).start()
            Log.d("FabAnim", "startHandler()")
        }, 1000)
    }

    fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout?,
        child: FloatingActionButton,
        target: View?,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        if (coordinatorLayout != null) {
            if (target != null) {
                super.onNestedScroll(coordinatorLayout,
                    child,
                    target,
                    dxConsumed,
                    dyConsumed,
                    dxUnconsumed,
                    dyUnconsumed,
                    type)
            }
        }
        if (dyConsumed > 0) {
            Log.d("Scrolling", "Up")
            val layoutParams: CoordinatorLayout.LayoutParams =
                child.getLayoutParams() as CoordinatorLayout.LayoutParams
            val fab_bottomMargin: Int = layoutParams.bottomMargin
            child.animate().translationY((child.getHeight() + fab_bottomMargin).toFloat())
                .setInterpolator(LinearInterpolator()).start()
        } else if (dyConsumed < 0) {
            Log.d("Scrolling", "down")
            child.animate().translationY(0F).setInterpolator(LinearInterpolator()).start()
        }
    }

    fun onStartNestedScroll(
         coordinatorLayout: CoordinatorLayout?,
         child: FloatingActionButton?,
         directTargetChild: View?,
         target: View?,
        axes: Int,
        type: Int
    ): Boolean {
        if (mHandler != null) {
            mHandler!!.removeMessages(0)
            Log.d("Scrolling", "stopHandler()")
        }
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    companion object {
        private const val TAG = "ScrollingFABBehavior"
    }
}