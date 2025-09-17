package com.example.languagefun.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.languagefun.R
import com.example.languagefun.data.remote.dto.DashboardEntityDto

// RecyclerView Adapter for displaying a list of DashboardEntityDto items.
// Uses ListAdapter + DiffUtil for efficient list updates.
// Accepts a click listener lambda to handle item selection.
class EntityAdapter(
    private val onClick: (DashboardEntityDto) -> Unit // Callback triggered when an item is clicked
) : ListAdapter<DashboardEntityDto, EntityAdapter.VH>(Diff) {

    // ViewHolder class responsible for binding a single DashboardEntityDto to its layout
    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvAlbumTitle: TextView = itemView.findViewById(R.id.tvAlbumTitle)
        private val tvArtistName: TextView = itemView.findViewById(R.id.tvArtistName)
        private val tvReleaseYear: TextView = itemView.findViewById(R.id.tvReleaseYear)
        private val tvGenre: TextView = itemView.findViewById(R.id.tvGenre)
        private val tvTrackCount: TextView = itemView.findViewById(R.id.tvTrackCount)
        private val tvPopularTrack: TextView = itemView.findViewById(R.id.tvPopularTrack)

        // Bind entity data to the view components
        fun bind(item: DashboardEntityDto, onClick: (DashboardEntityDto) -> Unit) {
            // Required fields (always visible)
            tvAlbumTitle.text = item.albumTitle
            tvArtistName.text = item.artistName

            // Toggle optional fields' visibility depending on whether values exist
            tvReleaseYear.isVisible = item.releaseYear != null
            tvGenre.isVisible = !item.genre.isNullOrBlank()
            tvTrackCount.isVisible = item.trackCount != null
            tvPopularTrack.isVisible = !item.popularTrack.isNullOrBlank()

            // Set values if available
            item.releaseYear?.let { tvReleaseYear.text = it.toString() }
            item.genre?.let { tvGenre.text = it }
            item.trackCount?.let { tvTrackCount.text = "$it tracks" }
            item.popularTrack?.let { tvPopularTrack.text = "Popular Track: $it" }

            // Handle item click by passing the entity to the callback
            itemView.setOnClickListener { onClick(item) }
        }
    }

    // Create a new ViewHolder by inflating the item layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dashboard_entity, parent, false)
        return VH(v)
    }

    // Bind data to the given ViewHolder
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    // DiffUtil callback to efficiently update the list when data changes
    private object Diff : DiffUtil.ItemCallback<DashboardEntityDto>() {
        override fun areItemsTheSame(
            oldItem: DashboardEntityDto,
            newItem: DashboardEntityDto
        ): Boolean {
            // Use "albumTitle + artistName" as a simple unique key (can replace with id if available later)
            return oldItem.albumTitle == newItem.albumTitle &&
                    oldItem.artistName == newItem.artistName
        }

        override fun areContentsTheSame(
            oldItem: DashboardEntityDto,
            newItem: DashboardEntityDto
        ) = oldItem == newItem // Compare entire object for equality
    }
}




