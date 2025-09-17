package com.example.languagefun.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

// DTO classes for handling the Dashboard API response.
// Includes both the response wrapper (DashboardResponse) and individual entity details (DashboardEntityDto).
// These classes are mapped directly from the JSON response returned by the API.

// Represents the API response for the dashboard request
// Contains a list of entities (albums) and the total count of entities
data class DashboardResponse(
    @SerializedName("entities") val entities: List<DashboardEntityDto>, // List of album entities
    @SerializedName("entityTotal") val entityTotal: Int                 // Total number of entities returned
)

// Represents a single entity (album) in the dashboard response
// Implements Serializable so it can be safely passed between fragments/activities
data class DashboardEntityDto(
    @SerializedName("albumTitle")   val albumTitle: String,      // Title of the album
    @SerializedName("artistName")   val artistName: String,      // Name of the artist
    @SerializedName("releaseYear")  val releaseYear: Int? = null,// Year the album was released (nullable)
    @SerializedName("genre")        val genre: String? = null,   // Music genre of the album (nullable)
    @SerializedName("trackCount")   val trackCount: Int? = null, // Total number of tracks in the album (nullable)
    @SerializedName("description")  val description: String? = null, // Detailed description of the album (nullable)
    @SerializedName("popularTrack") val popularTrack: String? = null // The most popular track of the album (nullable)
) : Serializable
