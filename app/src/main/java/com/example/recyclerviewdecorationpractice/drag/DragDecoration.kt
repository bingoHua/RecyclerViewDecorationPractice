package com.example.recyclerviewdecorationpractice.drag

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.NinePatchDrawable
import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView

class DragDecoration(private val recyclerView: RecyclerView, private val viewHolder: RecyclerView.ViewHolder) : RecyclerView.ItemDecoration() {

    private var translateX = 0f
    private var translateY = 0f
    private var viewBitmap: Bitmap? = null
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    fun startDragging(translateX: Float, translateY: Float) {
        this.translateX = translateX
        this.translateY = translateY
        viewBitmap = createDraggingItemImage(viewHolder.itemView, null)
        recyclerView.addItemDecoration(this)
    }

    fun updatePosition(translateX: Float, translateY: Float) {
        this.translateX = translateX
        this.translateY = translateY
        ViewCompat.postInvalidateOnAnimation(recyclerView)
    }

    fun finishDragging() {
        recyclerView.removeItemDecoration(this)
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        viewBitmap?.let {
            canvas.translate(viewHolder.itemView.left + translateX, viewHolder.itemView.top + translateY)
            canvas.drawBitmap(it, 0f, 0f, paint)
        }
    }

    private fun createDraggingItemImage(v: View, shadow: NinePatchDrawable?): Bitmap? {
        val viewTop = v.top
        val viewLeft = v.left
        val viewWidth = v.width
        val viewHeight = v.height
        val canvasWidth: Int = viewWidth
        val canvasHeight: Int = viewHeight
        v.measure(
                View.MeasureSpec.makeMeasureSpec(viewWidth, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(viewHeight, View.MeasureSpec.EXACTLY))
        v.layout(viewLeft, viewTop, viewLeft + viewWidth, viewTop + viewHeight)
        val bitmap = Bitmap.createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        if (shadow != null) {
            shadow.setBounds(0, 0, canvasWidth, canvasHeight)
            shadow.draw(canvas)
        }
        val savedCount = canvas.save()
        // NOTE: Explicitly set clipping rect. This is required on Gingerbread.
        canvas.clipRect(0, 0, canvasWidth, canvasHeight)
        canvas.translate(0f, 0f)
        v.draw(canvas)
        canvas.restoreToCount(savedCount)
        return bitmap
    }

}