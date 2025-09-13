package com.example.languagefun

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.MaterialAutoCompleteTextView

/**
 * HomeActivity
 *
 * Purpose:
 * - Hosts the app’s main landing screen.
 * - Shows a language selector (exposed dropdown), a horizontal “Popular Courses” list,
 *   and a vertical “Daily Exercises” list.
 * - Wires up the bottom navigation (Home/Profile) and keeps the current tab highlighted.
 *
 * Notes:
 * - Uses simple in-memory lists to populate RecyclerViews (no networking in this demo).
 * - Item spacing is applied via SpacingItemDecoration so cards don’t stick together.
 * - Minimal activity transition when switching tabs to mimic a native bottom-nav feel.
 */
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the XML layout that contains:
        // - Top app bar + language dropdown
        // - Two RecyclerViews (courses, exercises)
        // - BottomNavigationView
        setContentView(R.layout.activity_home)

        // --- Language selector (Material "Exposed Dropdown") ---
        // We locate the AutoCompleteTextView inside a TextInputLayout and attach a simple adapter.
        // This keeps the UI lightweight but still gives a “picker” experience.
        val languageDropdown = findViewById<MaterialAutoCompleteTextView>(R.id.actvLanguage)
        languageDropdown.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,    // Built-in row layout; fine for a short list
                listOf("Chinese", "Arabic", "Japanese", "Spanish", "French")
            )
        )

        // --- Popular Courses (horizontal list) ---
        // Horizontally scrolling cards; each item is a “course” with a title + subtitle.
        // LinearLayoutManager(HORIZONTAL) gives the classic carousel feel.
        val rvCourses = findViewById<RecyclerView>(R.id.rvCourses).apply {
            layoutManager = LinearLayoutManager(
                this@HomeActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = CourseAdapter(
                listOf(
                    // imageRes is optional here; we focus on structure/layout first.
                    Course("Spanish for Beginners", "Daily Conversations"),
                    Course("French Basics", "Travel German"),   // kept as provided in your mock
                    Course("Japanese N5", "Hiragana & Katakana")
                )
            )
            // Add horizontal spacing so cards don’t touch each other or the screen edges.
            addItemDecoration(
                SpacingItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.space_12),
                    RecyclerView.HORIZONTAL
                )
            )
        }

        // --- Daily Exercises (vertical list) ---
        // A straightforward vertical feed of small tasks with an estimated time.
        val rvExercises = findViewById<RecyclerView>(R.id.rvExercises).apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = ExerciseAdapter(
                listOf(
                    Exercise("Practice Vocabulary", "5 minutes"),
                    Exercise("Listening Drill", "7 minutes"),
                    Exercise("Grammar Quick Test", "3 minutes")
                )
            )
            // Vertical spacing between rows to avoid a dense wall of text.
            addItemDecoration(
                SpacingItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.space_8),
                    RecyclerView.VERTICAL
                )
            )
        }

        // --- Bottom Navigation ---
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // We’re not doing any special edge-to-edge handling for this nav bar,
        // so remove any insets listener that could shift it unexpectedly.
        bottomNav.setOnApplyWindowInsetsListener(null)

        // Mark the current tab as selected. This keeps the icon/text tinted correctly.
        bottomNav.selectedItemId = R.id.navigation_home

        // Handle tab changes. If the user taps Profile, we launch ProfileActivity and
        // finish Home so we don’t stack multiple copies when switching back and forth.
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> true // Already on Home; consume the event.
                R.id.navigation_profile -> {
                    startActivity(
                        Intent(this, ProfileActivity::class.java)
                            // Clear any duplicate instances and reuse top if possible.
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    )
                    // Keep transitions subtle so it feels like a persistent bottom nav.
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                else -> false
            }
        }

        // Ignore reselects on the current tab (no need to scroll to top or refresh in this demo).
        bottomNav.setOnItemReselectedListener { /* no-op */ }
    }
}
