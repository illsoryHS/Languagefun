package com.example.languagefun

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.languagefun.data.remote.dto.DashboardEntityDto
import dagger.hilt.android.AndroidEntryPoint

/**
 * DetailsFragment
 *
 * 展示从 Dashboard 传来的实体的完整信息。
 * 布局：R.layout.fragment_details
 * 依赖：Navigation Component + Hilt
 */
@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    // 使用 Safe Args 接收传过来的 entity（已在 nav_graph 声明）
    private val args: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val entity: DashboardEntityDto? = args.entity

        view.findViewById<TextView>(R.id.tvTitle).text    = entity?.albumTitle.orEmpty()
        view.findViewById<TextView>(R.id.tvSubtitle).text = entity?.artistName.orEmpty()
        view.findViewById<TextView>(R.id.tvDesc).text     = entity?.description.orEmpty()

        // 如果你的 fragment_details.xml 里还有其他字段（如流派/曲目数/热门曲目），可按需继续绑定：
        // view.findViewById<TextView>(R.id.tvGenre).text        = entity?.genre.orEmpty()
        // view.findViewById<TextView>(R.id.tvTracks).text       = entity?.trackCount?.let { "$it tracks" }.orEmpty()
        // view.findViewById<TextView>(R.id.tvPopularTrack).text = entity?.popularTrack?.let { "Popular Track: $it" }.orEmpty()
    }
}

