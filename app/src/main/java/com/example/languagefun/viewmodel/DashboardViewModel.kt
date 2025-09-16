package com.example.languagefun.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.languagefun.data.repository.Nit3213Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repo: Nit3213Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Idle)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    fun loadDashboard(keypass: String) {
        _uiState.value = DashboardUiState.Loading
        viewModelScope.launch {
            val result = repo.getDashboard(keypass)
            result
                .onSuccess { resp ->
                    _uiState.value = DashboardUiState.Success(
                        entities = resp.entities,
                        total = resp.entityTotal
                    )
                }
                .onFailure {
                    _uiState.value = DashboardUiState.Error(it.message ?: "Failed to load dashboard")
                }
        }
    }

    fun reset() {
        _uiState.value = DashboardUiState.Idle
    }
}
