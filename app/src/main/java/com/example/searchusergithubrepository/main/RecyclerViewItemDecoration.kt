package com.example.searchusergithubrepository.main

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewItemDecoration(private val context: Context, private val spacing: Float) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val metrics = context.resources.displayMetrics
        val mPadding =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, spacing, metrics).toInt()
        outRect.bottom = mPadding
    }

}