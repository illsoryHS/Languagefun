package com.example.languagefun

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Custom ItemDecoration that adds spacing between items in a RecyclerView.
 *
 * Purpose:
 * - Prevents items from looking cramped by adding consistent gaps between them.
 * - Works for both vertical lists (spacing between rows) and horizontal lists (spacing between cards).
 *
 * Parameters:
 * @param spacePx     Amount of spacing in pixels to apply.
 * @param orientation Either RecyclerView.VERTICAL (default) or RecyclerView.HORIZONTAL.
 *
 * Behavior:
 * - HORIZONTAL list:
 *   → Adds spacing to the right of each card.
 *   → Adds spacing to the left of the very first card (so it’s not glued to the screen edge).
 * - VERTICAL list:
 *   → Adds spacing below each row.
 *   → Adds spacing above the very first row.
 *
 * Why it matters:
 * - Matches the visual padding you see in Figma/mockups.
 * - Keeps layouts visually balanced and easier to scan.
 */
class SpacingItemDecoration(
    private val spacePx: Int,
    private val orientation: Int = RecyclerView.VERTICAL
) : RecyclerView.ItemDecoration() {

    /**
     * Called for each item in the RecyclerView to adjust its surrounding spacing (offsets).
     * - We check the item position so only the first item gets extra padding at the start/top.
     */
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val pos = parent.getChildAdapterPosition(view)
        if (pos == RecyclerView.NO_POSITION) return // safety check: skip if item not yet bound

        if (orientation == RecyclerView.HORIZONTAL) {
            outRect.right = spacePx
            if (pos == 0) outRect.left = spacePx
        } else {
            outRect.bottom = spacePx
            if (pos == 0) outRect.top = spacePx
        }
    }
}

