package com.example.languagefun

import com.example.languagefun.data.remote.dto.AuthResponse
import com.example.languagefun.data.repository.Nit3213Repository
import com.example.languagefun.viewmodel.LoginUiState
import com.example.languagefun.viewmodel.LoginViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// Unit tests for LoginViewModel.
// Uses MockK to mock repository calls and kotlinx-coroutines-test to control coroutine execution.
@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(StandardTestDispatcher()) // Replace main dispatcher for coroutine tests

    @MockK
    lateinit var repo: Nit3213Repository // Mocked repository

    private lateinit var vm: LoginViewModel // System under test (SUT)

    @Before
    fun setup() {
        // Initialize MockK annotations and create ViewModel with mocked repo
        MockKAnnotations.init(this)
        vm = LoginViewModel(repo)
    }

    @Test
    fun loginSuccess_emitsSuccessWithKeypass() = runTest {
        // Given: repository returns a successful AuthResponse with keypass
        val key = "myTopic"
        coEvery { repo.login("footscray", "Allen", "8115345") } returns
                Result.success(AuthResponse(keypass = key))

        // When: calling login on the ViewModel
        vm.login("footscray", "Allen", "8115345")

        // Then: state should be Success with the correct keypass
        advanceUntilIdle() // Ensure coroutine completes
        val state = vm.uiState.value
        require(state is LoginUiState.Success)
        assertEquals(key, state.keypass)
    }

    @Test
    fun loginFailure_emitsError() = runTest {
        // Given: repository returns a failure with exception message
        coEvery { repo.login("footscray", "Allen", "badpass") } returns
                Result.failure(IllegalArgumentException("Login failed"))

        // When: calling login with invalid credentials
        vm.login("footscray", "Allen", "badpass")

        // Then: state should be Error with the expected error message
        advanceUntilIdle() // Ensure coroutine completes
        val state = vm.uiState.value
        require(state is LoginUiState.Error)
        assertEquals("Login failed", state.message)
    }
}

