package com.example.languagefun

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter class for the Achievements RecyclerView.
 *
 * Purpose:
 * - Connects a list of Achievement data objects to the UI (RecyclerView).
 * - Inflates the item layout for each achievement and fills in its data (icon, title, subtitle).
 * - Improves performance by recycling item views instead of creating them from scratch.
 *
 * This follows the standard RecyclerView pattern:
 * 1. ViewHolder (VH) holds references to views in one item.
 * 2. onCreateViewHolder inflates the XML layout into a ViewHolder.
 * 3. onBindViewHolder binds actual data to each ViewHolder.
 * 4. getItemCount tells RecyclerView how many items exist.
 */
class AchievementAdapter(private val items: List<Achievement>) :
    RecyclerView.Adapter<AchievementAdapter.VH>() {

    /**
     * ViewHolder (VH) class
     * - Stores references to the views inside one achievement item
     * - Avoids calling findViewById repeatedly (saves memory & improves speed)
     */
    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val icon: ImageView = v.findViewById(R.id.ivIcon)       // small icon for the achievement
        val title: TextView = v.findViewById(R.id.tvAchTitle)  // main achievement title
        val sub: TextView = v.findViewById(R.id.tvAchSub)      // optional subtitle/extra info
    }

    /**
     * Called when RecyclerView needs a new ViewHolder.
     * - Inflates the item_achievement.xml layout file
     * - Wraps it inside a VH object
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_achievement, parent, false)
        return VH(v)
    }

    /**
     * Called to display data at a specific position.
     * - Gets the Achievement object at the current list position
     * - Updates the ViewHolder views (icon, title, subtitle) with this data
     */
    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.icon.setImageResource(item.iconRes)
        holder.title.text = item.title
        holder.sub.text = item.subtitle
    }

    /**
     * Tells RecyclerView how many items are in the list.
     * - This controls how many rows will be drawn on screen
     */
    override fun getItemCount() = items.size
}

