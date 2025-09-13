package com.example.languagefun

import androidx.annotation.DrawableRes

/**
 * UI model representing a course item rendered in the horizontal list.
 *
 * @param title    Course title (max 2 lines in the card).
 * @param subtitle Short tagline or context for the course.
 * @param imageRes Optional drawable resource used as the cover image.
 *                 Pass 0 if you don't have an image; the card will fall back to the grey placeholder.
 */
data class Course(
    val title: String,
    val subtitle: String,
    @DrawableRes val imageRes: Int = 0
)
