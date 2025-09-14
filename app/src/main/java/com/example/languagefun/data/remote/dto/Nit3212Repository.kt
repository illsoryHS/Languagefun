package com.example.languagefun.data.repository

import com.example.languagefun.data.remote.Nit3213Api
import com.example.languagefun.data.remote.dto.AuthRequest
import com.example.languagefun.data.remote.dto.AuthResponse
import com.example.languagefun.data.remote.dto.DashboardResponse
import javax.inject.Inject

class Nit3213Repository @Inject constructor(
    private val api: Nit3213Api
) {
    suspend fun login(campus: String, username: String, password: String): Result<AuthResponse> =
        runCatching { api.login(campus, AuthRequest(username, password)) }

    suspend fun getDashboard(keypass: String): Result<DashboardResponse> =
        runCatching { api.getDashboard(keypass) }
}
