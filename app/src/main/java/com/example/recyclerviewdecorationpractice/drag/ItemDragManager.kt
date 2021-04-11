package com.example.recyclerviewdecorationpractice.drag

import android.os.Handler
import android.os.Looper
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

class ItemDragManager constructor(private val recyclerView: RecyclerView) {

    private val itemTouchListener: RecyclerView.OnItemTouchListener
    private var isLongPressed = false
    private var offsetX = 0f
    private var offsetY = 0f
    private var downPositionX = 0f
    private var downPositionY = 0f
    private val handler: Handler = Handler(Looper.getMainLooper())
    private var dragDecoration: DragDecoration? = null

    private var mLongPressed = Runnable {
        isLongPressed = true
    }
    private var isDragging = false

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
                        downPositionX = e.x
                        downPositionY = e.y
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
                        downPositionX = e.x
                        downPositionY = e.y
                    }
                    MotionEvent.ACTION_MOVE -> {
                        offsetX = e.x - downPositionX
                        offsetY = e.y - downPositionY
                        checkAndStartDragging(offsetX, offsetY)
                    }
                    MotionEvent.ACTION_UP -> {
                        isLongPressed = false
                        isDragging = false
                        finishDragging()
                    }
                }
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                this@ItemDragManager.onRequestDisallowInterceptTouchEvent(disallowIntercept)
            }
        }

        recyclerView.addOnItemTouchListener(itemTouchListener)
    }

    private fun checkAndStartDragging(x: Float, y: Float) {
        if (isLongPressed && !isDragging) {
            findChildViewHolderUnderWithoutTranslation(recyclerView, downPositionX, downPositionY)?.let {
                dragDecoration = DragDecoration(recyclerView, it)
                dragDecoration?.startDragging(x, y)
                isDragging = true
            }
        } else if (isDragging) {
            dragDecoration?.updatePosition(x, y)
        }
    }

    private fun finishDragging() {
        dragDecoration?.finishDragging()
    }

    fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }

}