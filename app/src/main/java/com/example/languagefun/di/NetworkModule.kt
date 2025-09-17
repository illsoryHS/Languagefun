package com.example.languagefun.di

import com.example.languagefun.data.remote.Nit3213Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// Dependency Injection module for providing network-related components.
// Uses Hilt to supply singletons of OkHttpClient, Retrofit, and the Nit3213Api interface.
// All provided instances are available at the application (Singleton) scope.

private const val BASE_URL = "https://nit3213api.onrender.com/"

@Module
@InstallIn(SingletonComponent::class) // This module is installed in the application-wide SingletonComponent
object NetworkModule {

    // Provides a singleton instance of OkHttpClient with logging interceptor.
    // The logging interceptor prints network request/response details to Logcat for debugging.
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Logs request and response bodies
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging) // Attach the logging interceptor
            .build()
    }

    // Provides a singleton instance of Retrofit.
    // Configured with the base URL, OkHttp client, and Gson converter for JSON parsing.
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL) // API base URL
            .client(client) // Use OkHttp client with logging
            .addConverterFactory(GsonConverterFactory.create()) // Convert JSON to Kotlin data classes
            .build()

    // Provides a singleton instance of the Nit3213Api interface.
    // Retrofit generates the implementation for this API service.
    @Provides
    @Singleton
    fun provideNit3213Api(retrofit: Retrofit): Nit3213Api =
        retrofit.create(Nit3213Api::class.java)
}
