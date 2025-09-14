package com.example.languagefun.data.remote.dto

data class AuthRequest(
    val username: String,
    val password: String
)

data class AuthResponse(
    val keypass: String
)
