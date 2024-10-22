package com.example.cactusnotes.note.list

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cactusnotes.R

class NotesItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        val spacing = parent.context.resources.getDimensionPixelSize(
            R.dimen.note_list_item_decoration_spacing
        )

        val isOnFirstLine = position < 2
        val isAtRight =
            (view.layoutParams as StaggeredGridLayoutManager.LayoutParams).spanIndex == 1
        if (isAtRight) {
            outRect.left = spacing / 2
            outRect.right = spacing
        } else {
            outRect.left = spacing
            outRect.right = spacing / 2
        }
        outRect.bottom = spacing

        if (isOnFirstLine) {
            outRect.top = spacing
        }
    }
}