package com.example.languagefun

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Custom Application class that serves as the entry point for the app.
// Annotated with @HiltAndroidApp to enable Hilt's dependency injection system.
// Hilt generates all required components and sets up the dependency graph here.
@HiltAndroidApp
class LanguageFunApp : Application()
