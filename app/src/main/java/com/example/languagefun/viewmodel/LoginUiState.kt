package com.example.languagefun.viewmodel

// Represents different UI states for the Login screen.
// This sealed class ensures the UI (e.g., LoginFragment) can react
// appropriately to each possible state of the login process.
sealed class LoginUiState {

    // Initial state before any login attempt
    data object Idle : LoginUiState()

    // State shown while the login request is in progress
    data object Loading : LoginUiState()

    // State when login is successful
    // Contains the "keypass" string returned by the API
    data class Success(val keypass: String) : LoginUiState()

    // State representing an error condition (e.g., invalid credentials, network failure)
    data class Error(val message: String) : LoginUiState()
}
