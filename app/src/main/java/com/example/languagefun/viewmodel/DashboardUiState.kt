package com.example.languagefun.viewmodel

import com.example.languagefun.data.remote.dto.DashboardEntityDto

sealed class DashboardUiState {
    data object Idle : DashboardUiState()
    data object Loading : DashboardUiState()
    data class Success(
        val entities: List<DashboardEntityDto>,
        val total: Int
    ) : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}
