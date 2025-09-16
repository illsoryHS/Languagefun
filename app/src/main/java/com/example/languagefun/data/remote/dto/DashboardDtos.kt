package com.example.languagefun.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DashboardResponse(
    @SerializedName("entities") val entities: List<DashboardEntityDto>,
    @SerializedName("entityTotal") val entityTotal: Int
)

data class DashboardEntityDto(
    @SerializedName("albumTitle")   val albumTitle: String,
    @SerializedName("artistName")   val artistName: String,
    @SerializedName("releaseYear")  val releaseYear: Int? = null,
    @SerializedName("genre")        val genre: String? = null,
    @SerializedName("trackCount")   val trackCount: Int? = null,
    @SerializedName("description")  val description: String? = null,
    @SerializedName("popularTrack") val popularTrack: String? = null
) : Serializable
