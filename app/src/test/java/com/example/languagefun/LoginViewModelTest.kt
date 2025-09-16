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

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(StandardTestDispatcher())

    @MockK
    lateinit var repo: Nit3213Repository

    private lateinit var vm: LoginViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        vm = LoginViewModel(repo)
    }

    @Test
    fun loginSuccess_emitsSuccessWithKeypass() = runTest {
        // Given
        val key = "myTopic"
        coEvery { repo.login("footscray", "Allen", "8115345") } returns
                Result.success(AuthResponse(keypass = key))

        // When
        vm.login("footscray", "Allen", "8115345")

        // Then
        advanceUntilIdle()
        val state = vm.uiState.value
        require(state is LoginUiState.Success)
        assertEquals(key, state.keypass)
    }

    @Test
    fun loginFailure_emitsError() = runTest {
        // Given
        coEvery { repo.login("footscray", "Allen", "badpass") } returns
                Result.failure(IllegalArgumentException("Login failed"))

        // When
        vm.login("footscray", "Allen", "badpass")

        // Then
        advanceUntilIdle()
        val state = vm.uiState.value
        require(state is LoginUiState.Error)
        assertEquals("Login failed", state.message)
    }
}
