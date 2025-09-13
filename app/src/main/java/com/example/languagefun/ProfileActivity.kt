package com.example.languagefun

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * ProfileActivity
 *
 * Purpose:
 * - Hosts the user profile screen with two sections:
 *   1) "Achievements" shown as a 2-column grid of badges.
 *   2) "Progress Overview" shown as a horizontal list of progress cards.
 * - Wires up the bottom navigation (Home/Profile) and highlights the current tab.
 *
 * Notes:
 * - Uses simple in-memory lists for demo data (no persistence/network in this part).
 * - Adds item spacing to the achievements grid via a custom ItemDecoration.
 * - setHasFixedSize(true) is used where list dimensions don’t change with content updates,
 *   allowing small performance wins during layout passes.
 */
class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the XML that defines the profile header, achievements grid,
        // horizontal progress list, and the bottom nav bar.
        setContentView(R.layout.activity_profile)

        // --- Achievements: 2-column grid of small badge cards ---
        val rvAchievements = findViewById<RecyclerView>(R.id.rvAchievements)
        rvAchievements.layoutManager = GridLayoutManager(this, 2)
        rvAchievements.setHasFixedSize(true) // grid item size is stable → smoother scrolling

        // Convert spacing from dp to px. This keeps visual gaps even on dense screens.
        // FYI: 1dp is very tight; 8dp–12dp is a common choice for card grids.
        val spacing = (resources.displayMetrics.density * 1).toInt() // consider using 8 for more breathing room
        rvAchievements.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
            ) {
                // Apply equal padding on all sides so tiles don’t stick together or to edges.
                outRect.set(spacing, spacing, spacing, spacing)
            }
        })

        // Adapter gets a static list of demo badges; each badge displays an icon + label.
        rvAchievements.adapter = AchievementAdapter(
            listOf(
                Achievement(R.drawable.ic_star,        "Completed Lessons"),
                Achievement(R.drawable.ic_inventory_2, "Collectibles"),
                Achievement(R.drawable.ic_flag,        "Challenges"),
                Achievement(R.drawable.ic_target,      "Daily Goals")
            )
        )

        // --- Progress Overview: horizontally scrolling “pill” cards ---
        val rvProgress = findViewById<RecyclerView>(R.id.rvProgress)
        rvProgress.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvProgress.setHasFixedSize(true) // card size doesn’t change with data → minor optimization
        rvProgress.adapter = ProgressAdapter(
            listOf(
                ProgressCard("Spanish Level 1"),
                ProgressCard("French Level 1")
            )
        )

        // --- Bottom Navigation ---
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // No special edge-to-edge behavior for the nav bar here, so clear any insets listener
        // that could push it upward on devices with gesture nav.
        bottomNav.setOnApplyWindowInsetsListener(null)

        // Highlight the current tab (Profile) so the UI state matches the screen.
        bottomNav.selectedItemId = R.id.navigation_profile

        // Handle tab switching. Moving to Home finishes this activity so we don’t stack
        // multiple copies when the user bounces between tabs.
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(
                        Intent(this, HomeActivity::class.java)
                            // Reuse an existing instance if it’s already on top.
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    )
                    // Keep transitions subtle so it feels like a persistent bottom nav.
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                R.id.navigation_profile -> true // Already on Profile; consume the event.
                else -> false
            }
        }

        // Ignore “reselect” on the current tab; no scroll-to-top or refresh in this demo.
        bottomNav.setOnItemReselectedListener { /* no-op */ }
    }
}
