package com.example.languagefun

/**
 * UI model representing a daily exercise item.
 *
 * @param title    Short, bold title shown on the first line.
 * @param duration Secondary info (e.g., "5 minutes") shown on the second line.
 */
data class Exercise(
    val title: String,
    val duration: String
)

