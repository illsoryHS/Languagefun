@file:OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)

package com.example.languagefun

import com.example.languagefun.data.remote.dto.DashboardEntityDto
import com.example.languagefun.data.remote.dto.DashboardResponse
import com.example.languagefun.data.repository.Nit3213Repository
import com.example.languagefun.viewmodel.DashboardUiState
import com.example.languagefun.viewmodel.DashboardViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DashboardViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(StandardTestDispatcher())

    @MockK
    lateinit var repo: Nit3213Repository

    private lateinit var vm: DashboardViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        vm = DashboardViewModel(repo)
    }

    @Test
    fun getDashboard_success_emitsSuccess() = runTest {
        // Given
        val list = listOf(
            DashboardEntityDto(
                albumTitle = "Random Access Memories",
                artistName = "Daft Punk",
                releaseYear = 2013,
                genre = "Electronic",
                trackCount = 13,
                description = "Won the Grammy for Album of the Year",
                popularTrack = "Get Lucky"
            ),
            DashboardEntityDto(
                albumTitle = "1989",
                artistName = "Taylor Swift",
                releaseYear = 2014,
                genre = "Pop",
                trackCount = 13,
                description = "Grammy-winning pop album",
                popularTrack = "Blank Space"
            )
        )
        coEvery { repo.getDashboard("myTopic") } returns
                Result.success(DashboardResponse(entities = list, entityTotal = list.size))

        // When
        vm.loadDashboard("myTopic")
        advanceUntilIdle()

        // Then
        val state = vm.uiState.value
        require(state is DashboardUiState.Success)
        assertEquals(2, state.total)
        assertEquals("Random Access Memories", state.entities[0].albumTitle)
        assertEquals("Grammy-winning pop album", state.entities[1].description)
    }

    @Test
    fun getDashboard_failure_emitsError() = runTest {
        // Given
        coEvery { repo.getDashboard("badKey") } returns
                Result.failure(IllegalStateException("Network error"))

        // When
        vm.loadDashboard("badKey")
        advanceUntilIdle()

        // Then
        val state = vm.uiState.value
        require(state is DashboardUiState.Error)
        assertEquals("Network error", state.message)
    }
}

