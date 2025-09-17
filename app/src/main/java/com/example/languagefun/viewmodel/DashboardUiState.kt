package com.example.languagefun.viewmodel

import com.example.languagefun.data.remote.dto.DashboardEntityDto

// Represents different UI states for the Dashboard screen.
// This sealed class ensures all possible states are handled explicitly
// in the ViewModel and UI layer (e.g., when observing LiveData/StateFlow).
sealed class DashboardUiState {

    // Initial state before any action is taken
    data object Idle : DashboardUiState()

    // State shown while data is being loaded (e.g., network request in progress)
    data object Loading : DashboardUiState()

    // State when data is successfully loaded
    // Contains the list of entities and total count
    data class Success(
        val entities: List<DashboardEntityDto>, // List of dashboard items
        val total: Int                          // Total number of items
    ) : DashboardUiState()

    // State representing an error condition (e.g., network failure, API error)
    data class Error(val message: String) : DashboardUiState()
}
