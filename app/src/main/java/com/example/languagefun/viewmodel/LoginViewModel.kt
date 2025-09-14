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
class LoginViewModel @Inject constructor(
    private val repo: Nit3213Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    /**
     * campus: "footscray" | "sydney" | "br"
     * username: 你的英文名
     * password: 学号（不带 s）
     */
    fun login(campus: String, username: String, password: String) {
        _uiState.value = LoginUiState.Loading
        viewModelScope.launch {
            val result = repo.login(campus, username, password)
            result
                .onSuccess { _uiState.value = LoginUiState.Success(it.keypass) }
                .onFailure { _uiState.value = LoginUiState.Error(it.message ?: "Login failed") }
        }
    }

    fun reset() {
        _uiState.value = LoginUiState.Idle
    }
}

