package com.example.languagefun.data.remote.dto

//Represents the request body sent to the API when logging in
data class AuthRequest(
    val username: String,
    val password: String
)

// Represents the response returned by the API after a successful login
// The API provides a "keypass" which is required to fetch dashboard data
data class AuthResponse(
    val keypass: String // Unique identifier used to authorize dashboard requests
)
