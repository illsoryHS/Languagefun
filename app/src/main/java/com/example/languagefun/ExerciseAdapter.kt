package com.example.languagefun

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter for the vertical "Daily Exercises" RecyclerView.
 *
 * Purpose:
 * - Displays a list of [Exercise] objects, each with a title and duration.
 * - Inflates the XML layout (item_exercise) for each row.
 * - Optionally supports click events so the Activity/Fragment can react to taps.
 *
 * Clean Code Notes:
 * - ViewHolder stores references to views (title, duration) to avoid repeated lookups.
 * - Binding logic is straightforward; more advanced features (icons, progress bars)
 *   can be added later without changing the overall structure.
 */
class ExerciseAdapter(
    private val items: List<Exercise>,
    private val onItemClick: ((Exercise) -> Unit)? = null, // callback if user taps an exercise
    @LayoutRes private val itemLayoutRes: Int = R.layout.item_exercise
) : RecyclerView.Adapter<ExerciseAdapter.VH>() {

    /**
     * ViewHolder:
     * - Represents one exercise row in the list.
     * - Holds references to its TextViews so they can be updated quickly.
     */
    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)       // exercise name
        val duration: TextView = itemView.findViewById(R.id.tvDuration) // estimated time
    }

    /**
     * Called when RecyclerView needs a new ViewHolder.
     * - Inflates item_exercise.xml into a View and wraps it in VH.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(itemLayoutRes, parent, false)
        return VH(view)
    }

    /**
     * Called to bind data at the given position.
     * - Updates the row with the exercise’s title and duration.
     * - Attaches a click listener if provided.
     */
    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.title.text = item.title
        holder.duration.text = item.duration

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(item)
        }
    }

    /** Returns how many exercise rows should be displayed. */
    override fun getItemCount(): Int = items.size
}

