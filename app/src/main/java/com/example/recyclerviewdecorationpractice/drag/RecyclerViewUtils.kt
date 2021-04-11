package com.example.recyclerviewdecorationpractice.drag

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

fun findChildViewHolderUnderWithoutTranslation(rv: RecyclerView, x: Float, y: Float): RecyclerView.ViewHolder? {
    val child: View? = findChildViewUnderWithoutTranslation(rv, x, y)
    return if (child != null) rv.getChildViewHolder(child) else null
}

private fun findChildViewUnderWithoutTranslation(parent: ViewGroup, x: Float, y: Float): View? {
    val count = parent.childCount
    for (i in count - 1 downTo 0) {
        val child = parent.getChildAt(i)
        if (x >= child.left && x <= child.right && y >= child.top && y <= child.bottom) {
            return child
        }
    }
    return null
}
