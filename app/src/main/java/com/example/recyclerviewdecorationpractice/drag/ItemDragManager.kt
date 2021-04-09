package com.example.recyclerviewdecorationpractice.drag

import android.os.Handler
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

class ItemDragManager constructor(recyclerView: RecyclerView) {

    private val itemTouchListener: RecyclerView.OnItemTouchListener
    private var isLongPressed = false
    val handler: Handler = Handler()
    var mLongPressed = Runnable {
        isLongPressed = true
    }

    private val gestureDetector: GestureDetectorCompat = GestureDetectorCompat(recyclerView.context, object : GestureDetector.OnGestureListener {
        override fun onDown(e: MotionEvent?): Boolean {
            return false
        }

        override fun onShowPress(e: MotionEvent?) {

        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return false

        }

        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
            return false

        }

        override fun onLongPress(e: MotionEvent?) {
            isLongPressed = true
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            return false
        }

    })

    init {

        itemTouchListener = object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                when (e.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        handler.postDelayed(mLongPressed, ViewConfiguration.getLongPressTimeout().toLong())
                    }
                    MotionEvent.ACTION_MOVE -> {
                        handler.removeCallbacks(mLongPressed)
                    }
                    MotionEvent.ACTION_UP -> {
                    }
                }
                return isLongPressed
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                when (e.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        handler.postDelayed(mLongPressed, ViewConfiguration.getLongPressTimeout().toLong())
                    }
                    MotionEvent.ACTION_MOVE -> {
                    }
                    MotionEvent.ACTION_UP -> {
                        isLongPressed = false
                    }
                }
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                this@ItemDragManager.onRequestDisallowInterceptTouchEvent(disallowIntercept)
            }
        }

        recyclerView.addOnItemTouchListener(itemTouchListener)

    }

    private fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        when (e.actionMasked) {
            MotionEvent.ACTION_DOWN -> {

            }
            MotionEvent.ACTION_MOVE -> {
            }
            MotionEvent.ACTION_UP -> {

            }
        }
    }

    fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent) {

    }

    fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }

}