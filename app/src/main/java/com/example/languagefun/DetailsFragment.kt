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

// Fragment responsible for displaying full details of a selected Dashboard entity.
// Layout: R.layout.fragment_details (includes MaterialToolbar, NestedScrollView, and MaterialCardView)
// Dependencies: Navigation Component (Safe Args) + Hilt
@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    // Safe Args: receives the selected entity passed from DashboardFragment
    private val args: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val entity: DashboardEntityDto? = args.entity

        // Configure the toolbar: set title and back navigation
        view.findViewById<MaterialToolbar>(R.id.toolbar)?.apply {
            title = getString(R.string.details_title) // Title is defined in strings.xml
            setNavigationIcon(com.google.android.material.R.drawable.ic_arrow_back_black_24)
            setNavigationOnClickListener { findNavController().navigateUp() }
        }

        // View references
        val tvTitle        = view.findViewById<TextView>(R.id.tvTitle)
        val tvSubtitle     = view.findViewById<TextView>(R.id.tvSubtitle)
        val tvDesc         = view.findViewById<TextView>(R.id.tvDesc)
        val tvGenre        = view.findViewById<TextView?>(R.id.tvGenre)
        val tvTracks       = view.findViewById<TextView?>(R.id.tvTracks)
        val tvPopularTrack = view.findViewById<TextView?>(R.id.tvPopularTrack)

        // Bind required fields
        tvTitle.text    = entity?.albumTitle.orEmpty()
        tvSubtitle.text = entity?.artistName.orEmpty()
        tvDesc.text     = entity?.description.orEmpty()

        // Bind optional fields (hide view if null/blank to avoid empty placeholders)
        tvGenre?.bindTextOrGone(entity?.genre)
        tvTracks?.bindTextOrGone(entity?.trackCount?.let { "$it tracks" })
        tvPopularTrack?.bindTextOrGone(entity?.popularTrack?.let { "Popular Track: $it" })
    }

    // Extension function: sets text if non-empty, hides view if null/blank
    private fun TextView.bindTextOrGone(textOrNull: CharSequence?) {
        if (textOrNull.isNullOrBlank()) {
            isGone = true
        } else {
            isGone = false
            text = textOrNull
        }
    }
}



