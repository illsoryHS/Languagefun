package com.example.languagefun

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.MaterialToolbar
import com.example.languagefun.data.remote.dto.DashboardEntityDto
import dagger.hilt.android.AndroidEntryPoint

/**
 * DetailsFragment
 *
 * 展示从 Dashboard 传来的实体的完整信息。
 * 布局：R.layout.fragment_details（含 MaterialToolbar + NestedScrollView + Card）
 * 依赖：Navigation Component + Hilt
 */
@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val entity: DashboardEntityDto? = args.entity

        // --- Toolbar：标题 + 返回键 ---
        view.findViewById<MaterialToolbar>(R.id.toolbar)?.apply {
            title = getString(R.string.details_title) // 建议在 strings.xml 新增 <string name="details_title">Details</string>
            setNavigationIcon(com.google.android.material.R.drawable.ic_arrow_back_black_24)
            setNavigationOnClickListener { findNavController().navigateUp() }
        }

        // --- 视图引用 ---
        val tvTitle        = view.findViewById<TextView>(R.id.tvTitle)
        val tvSubtitle     = view.findViewById<TextView>(R.id.tvSubtitle)
        val tvDesc         = view.findViewById<TextView>(R.id.tvDesc)
        val tvGenre        = view.findViewById<TextView?>(R.id.tvGenre)
        val tvTracks       = view.findViewById<TextView?>(R.id.tvTracks)
        val tvPopularTrack = view.findViewById<TextView?>(R.id.tvPopularTrack)

        // --- 主要字段 ---
        tvTitle.text    = entity?.albumTitle.orEmpty()
        tvSubtitle.text = entity?.artistName.orEmpty()
        tvDesc.text     = entity?.description.orEmpty()

        // --- 可选字段：判空隐藏，避免“占位的空行” ---
        tvGenre?.bindTextOrGone(entity?.genre)
        tvTracks?.bindTextOrGone(entity?.trackCount?.let { "$it tracks" })
        tvPopularTrack?.bindTextOrGone(entity?.popularTrack?.let { "Popular Track: $it" })
    }

    // 小工具：空则 GONE，非空则显示文本
    private fun TextView.bindTextOrGone(textOrNull: CharSequence?) {
        if (textOrNull.isNullOrBlank()) {
            isGone = true
        } else {
            isGone = false
            text = textOrNull
        }
    }
}


