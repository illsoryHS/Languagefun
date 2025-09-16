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

class EntityAdapter(
    private val onClick: (DashboardEntityDto) -> Unit
) : ListAdapter<DashboardEntityDto, EntityAdapter.VH>(Diff) {

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvAlbumTitle: TextView = itemView.findViewById(R.id.tvAlbumTitle)
        private val tvArtistName: TextView = itemView.findViewById(R.id.tvArtistName)
        private val tvReleaseYear: TextView = itemView.findViewById(R.id.tvReleaseYear)
        private val tvGenre: TextView = itemView.findViewById(R.id.tvGenre)
        private val tvTrackCount: TextView = itemView.findViewById(R.id.tvTrackCount)
        private val tvPopularTrack: TextView = itemView.findViewById(R.id.tvPopularTrack)

        fun bind(item: DashboardEntityDto, onClick: (DashboardEntityDto) -> Unit) {
            // 标题/副标题
            tvAlbumTitle.text = item.albumTitle
            tvArtistName.text = item.artistName

            // 可空字段的显示与隐藏
            tvReleaseYear.isVisible = item.releaseYear != null
            tvGenre.isVisible = !item.genre.isNullOrBlank()
            tvTrackCount.isVisible = item.trackCount != null
            tvPopularTrack.isVisible = !item.popularTrack.isNullOrBlank()

            item.releaseYear?.let { tvReleaseYear.text = it.toString() }
            item.genre?.let { tvGenre.text = it }
            item.trackCount?.let { tvTrackCount.text = "$it tracks" }
            item.popularTrack?.let { tvPopularTrack.text = "Popular Track: $it" }

            itemView.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dashboard_entity, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    private object Diff : DiffUtil.ItemCallback<DashboardEntityDto>() {
        override fun areItemsTheSame(
            oldItem: DashboardEntityDto,
            newItem: DashboardEntityDto
        ): Boolean {
            // 用 “albumTitle + artistName” 作为简易唯一键（若后续有 id 再替换）
            return oldItem.albumTitle == newItem.albumTitle &&
                    oldItem.artistName == newItem.artistName
        }

        override fun areContentsTheSame(
            oldItem: DashboardEntityDto,
            newItem: DashboardEntityDto
        ) = oldItem == newItem
    }
}



