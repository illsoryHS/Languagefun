package com.example.languagefun

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint

// MainActivity serves as the single-activity host for the entire app.
// It sets up the Navigation Component with a NavHostFragment that manages all fragments.
// Layout: R.layout.activity_main (contains the nav_host_container).
// Dependencies: Navigation Component + Hilt.
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Only add NavHostFragment when activity is created for the first time
        if (savedInstanceState == null) {
            // Create NavHostFragment using the navigation graph (nav_graph.xml)
            val navHost = NavHostFragment.create(R.navigation.nav_graph)

            // Attach the NavHostFragment to the container and set it as the primary navigation host
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_container, navHost) // Replace container with NavHost
                .setPrimaryNavigationFragment(navHost)    // Ensures Back button works with NavController
                .commitNow()
        }
    }
}

