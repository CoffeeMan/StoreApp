package com.epam.grocerystoreapp.presentation.screens.cart

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class CartItemDecoration(
    private val mDivider: Drawable,
) : ItemDecoration() {

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.getChildAt(0) != null) drawTopLine(canvas, parent)
        drawDividers(canvas, parent)
        if (parent.getChildAt(parent.childCount - 1) != null) drawBottomLine(canvas, parent)
    }

    private fun drawDividers(canvas: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        for (i in 0 until parent.childCount) {
            val child: View = parent.getChildAt(i)
            val params = child.layoutParams as? RecyclerView.LayoutParams
            if (params != null) {
                val top: Int = child.bottom + params.bottomMargin
                val bottom = top + mDivider.intrinsicHeight
                mDivider.setBounds(left, top, right, bottom)
                mDivider.draw(canvas)
            }
        }
    }

    private fun drawBottomLine(canvas: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val child: View = parent.getChildAt(parent.childCount - 1)
        val params = child.layoutParams as RecyclerView.LayoutParams
        val top: Int = child.bottom + params.bottomMargin
        val bottom = top + mDivider.intrinsicHeight
        mDivider.setBounds(left, top, right, bottom)
        mDivider.draw(canvas)
    }

    private fun drawTopLine(canvas: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val child: View = parent.getChildAt(0)
        val params = child.layoutParams as RecyclerView.LayoutParams
        val top: Int = child.top + params.topMargin
        val bottom = top + mDivider.intrinsicHeight
        mDivider.setBounds(left, top, right, bottom)
        mDivider.draw(canvas)
    }

}