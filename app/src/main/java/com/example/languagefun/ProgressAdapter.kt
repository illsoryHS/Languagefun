package com.example.languagefun

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter for the horizontal "Progress Overview" RecyclerView in the Profile screen.
 *
 * Purpose:
 * - Displays a series of [ProgressCard] objects (e.g., "Spanish Level 1").
 * - Each card shows the course name or progress label.
 *
 * Clean Code Notes:
 * - Simple one-field binding (just course name), but structure allows for easy extension
 *   (e.g., progress bars, icons, or badges later on).
 * - ViewHolder pattern avoids repeated findViewById calls for better performance.
 */
class ProgressAdapter(private val items: List<ProgressCard>) :
    RecyclerView.Adapter<ProgressAdapter.VH>() {

    /**
     * ViewHolder:
     * - Holds reference to the TextView that displays the course/progress name.
     */
    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val name: TextView = v.findViewById(R.id.tvCourseName) // label inside the card
    }

    /**
     * Called when RecyclerView needs a new ViewHolder.
     * - Inflates the item_progress_card.xml layout.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_progress_card, parent, false)
        return VH(v)
    }

    /**
     * Called to bind data at a given position.
     * - Updates the card’s text with the course name from [ProgressCard].
     */
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.name.text = items[position].courseName
    }

    /** Returns how many progress cards should be displayed. */
    override fun getItemCount() = items.size
}

