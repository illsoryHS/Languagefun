package com.example.languagefun

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.languagefun.ui.dashboard.EntityAdapter
import com.example.languagefun.viewmodel.DashboardUiState
import com.example.languagefun.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

// Fragment responsible for displaying the dashboard screen.
// 1) Fetches and displays a list of entities using the keypass from login.
// 2) Navigates to DetailsFragment when an item is clicked (via Safe Args).
// 3) Uses the layout file: R.layout.fragment_home.
@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_home) {

    private val vm: DashboardViewModel by viewModels() // Injected ViewModel instance
    private lateinit var recycler: RecyclerView        // RecyclerView for displaying entities
    private lateinit var adapter: EntityAdapter        // Adapter for binding data to RecyclerView

    // Retrieve the keypass argument passed from LoginFragment (Safe Args)
    private val args: DashboardFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView and Adapter
        recycler = view.findViewById(R.id.recyclerEntities)
        adapter = EntityAdapter(onClick = { entity ->
            // Navigate to DetailsFragment using Safe Args, passing the clicked entity
            val action = DashboardFragmentDirections.actionDashboardToDetails(entity)
            findNavController().navigate(action)
        })
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        // Load dashboard data using the provided keypass
        val keypass = args.keypass
        if (!keypass.isNullOrEmpty()) {
            vm.loadDashboard(keypass)
        } else {
            Toast.makeText(requireContext(), "Missing keypass", Toast.LENGTH_LONG).show()
        }

        // Observe the UI state and react to changes
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiState.collect { state ->
                    when (state) {
                        is DashboardUiState.Idle -> Unit // No action
                        is DashboardUiState.Loading ->
                            Toast.makeText(requireContext(), "Loading dashboard...", Toast.LENGTH_SHORT).show()

                        is DashboardUiState.Success ->
                            adapter.submitList(state.entities) // Update RecyclerView with new data

                        is DashboardUiState.Error ->
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}


