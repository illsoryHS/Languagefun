package com.example.languagefun

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

// JUnit Rule that swaps the main dispatcher with a TestDispatcher during tests.
// This ensures that coroutines using Dispatchers.Main are executed in a controlled test environment.
// Helps to avoid issues when testing ViewModels and other coroutine-based components.
@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val dispatcher: TestDispatcher = StandardTestDispatcher() // Default dispatcher used in tests
) : TestWatcher() {

    // Called before each test starts
    override fun starting(description: Description) {
        super.starting(description)
        // Replace the Main dispatcher with the test dispatcher
        Dispatchers.setMain(dispatcher)
    }

    // Called after each test finishes
    override fun finished(description: Description) {
        super.finished(description)
        // Reset the Main dispatcher back to the original
        Dispatchers.resetMain()
    }
}

