package com.example.languagefun.data.remote

import com.example.languagefun.data.remote.dto.AuthRequest
import com.example.languagefun.data.remote.dto.AuthResponse
import com.example.languagefun.data.remote.dto.DashboardResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Nit3213Api {

    // campus: "footscray" | "sydney" | "br"
    @POST("{campus}/auth")
    suspend fun login(
        @Path("campus") campus: String,
        @Body body: AuthRequest
    ): AuthResponse

    @GET("dashboard/{keypass}")
    suspend fun getDashboard(
        @Path("keypass") keypass: String
    ): DashboardResponse
}
