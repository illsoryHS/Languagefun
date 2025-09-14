package com.example.languagefun.data.remote.dto

data class Entity(
    val property1: String,
    val property2: String,
    val description: String
)

data class DashboardResponse(
    val entities: List<Entity>,
    val entityTotal: Int
)
