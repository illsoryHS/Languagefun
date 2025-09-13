package com.example.languagefun

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter for the "Popular Courses" horizontal RecyclerView.
 *
 * Purpose:
 * - Provides the link between a list of [Course] objects and the on-screen card UI.
 * - Inflates the XML layout (item_course) and fills each card with course data.
 * - Optionally handles item click events, so activities/fragments can react to user taps.
 *
 * Clean Code Notes:
 * - Uses a ViewHolder to cache references to child views (cover, title, subtitle).
 *   → Avoids calling findViewById repeatedly, which improves performance.
 * - Binding is kept simple; if more formatting is needed later (e.g., image loading from URL),
 *   that logic can be moved to helper functions or libraries.
 */
class CourseAdapter(
    private val items: List<Course>,
    private val onItemClick: ((Course) -> Unit)? = null,  // callback for item clicks (optional)
    @LayoutRes private val itemLayoutRes: Int = R.layout.item_course  // default layout resource
) : RecyclerView.Adapter<CourseAdapter.VH>() {

    /**
     * ViewHolder:
     * - Represents one row/item inside the RecyclerView.
     * - Holds strong references to its views so they can be quickly updated during binding.
     */
    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cover: ImageView = itemView.findViewById(R.id.ivCover)      // left/top image area
        val title: TextView = itemView.findViewById(R.id.tvTitle)       // course name
        val subtitle: TextView = itemView.findViewById(R.id.tvSubtitle) // supporting text
    }

    /**
     * Called when a new ViewHolder is needed.
     * - Inflates the layout resource (item_course) and wraps it in a VH.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(itemLayoutRes, parent, false)
        return VH(view)
    }

    /**
     * Called when data should be bound to an existing ViewHolder.
     * - Retrieves the [Course] at the current position.
     * - Updates the image, title, and subtitle accordingly.
     */
    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]

        // Handle cover image:
        // - If imageRes is set (non-zero), display it.
        // - Otherwise, leave it blank so the grey background placeholder from XML is visible.
        if (item.imageRes != 0) {
            holder.cover.setImageResource(item.imageRes)
        } else {
            holder.cover.setImageDrawable(null)
        }

        // Bind course text values.
        holder.title.text = item.title
        holder.subtitle.text = item.subtitle

        // Set up click listener if one was provided.
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(item)
        }
    }

    /** Returns the total number of items to show in the list. */
    override fun getItemCount(): Int = items.size
}


