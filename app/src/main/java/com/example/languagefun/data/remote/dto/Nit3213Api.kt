package com.example.languagefun.data.remote

import com.example.languagefun.data.remote.dto.AuthRequest
import com.example.languagefun.data.remote.dto.AuthResponse
import com.example.languagefun.data.remote.dto.DashboardResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// Retrofit interface defining the endpoints of the NIT3213 API.
// Provides suspend functions for user authentication and retrieving dashboard data.
interface Nit3213Api {

    // Sends a POST request to the authentication endpoint.
    // campus: "footscray" | "sydney" | "br"
    // Request body contains username and password wrapped in AuthRequest.
    // Returns an AuthResponse object with a keypass if login is successful.
    @POST("{campus}/auth")
    suspend fun login(
        @Path("campus") campus: String, // Campus identifier for API path
        @Body body: AuthRequest          // Login request body (username + password)
    ): AuthResponse

    // Sends a GET request to fetch dashboard data using the keypass from login response.
    // Returns a DashboardResponse containing the list of entities and total count.
    @GET("dashboard/{keypass}")
    suspend fun getDashboard(
        @Path("keypass") keypass: String // Unique identifier from AuthResponse
    ): DashboardResponse
}
