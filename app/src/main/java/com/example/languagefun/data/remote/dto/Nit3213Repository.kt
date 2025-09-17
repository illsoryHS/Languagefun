package com.example.languagefun.data.repository

import com.example.languagefun.data.remote.Nit3213Api
import com.example.languagefun.data.remote.dto.AuthRequest
import com.example.languagefun.data.remote.dto.AuthResponse
import com.example.languagefun.data.remote.dto.DashboardResponse
import javax.inject.Inject

// Repository layer for managing data operations related to the NIT3213 API.
// Acts as a mediator between the data source (Nit3213Api) and the rest of the app.
// Provides suspend functions that return Result-wrapped responses for safer error handling.
class Nit3213Repository @Inject constructor(
    private val api: Nit3213Api // Injected Retrofit API interface
) {
    // Makes a login request to the API.
    // Takes the campus identifier, username, and password, then returns AuthResponse wrapped in Result.
    suspend fun login(campus: String, username: String, password: String): Result<AuthResponse> =
        runCatching { api.login(campus, AuthRequest(username, password)) }

    // Fetches dashboard data from the API using the provided keypass.
    // Returns DashboardResponse wrapped in Result for error handling.
    suspend fun getDashboard(keypass: String): Result<DashboardResponse> =
        runCatching { api.getDashboard(keypass) }
}
