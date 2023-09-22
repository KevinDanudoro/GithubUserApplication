package com.bangkit.githubuserapplication.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bangkit.core.data.Resource
import com.bangkit.core.domain.usecase.GithubUserUseCase
import com.bangkit.githubuserapplication.utils.Dummy
import com.bangkit.githubuserapplication.utils.MainDispatcherRule
import com.bangkit.githubuserapplication.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var githubUserUseCase: GithubUserUseCase

    private lateinit var mainViewModel: MainViewModel
    private val dummyGithubUsers = Dummy.generateDummyGithubUsers()

    @Before
    fun setUp() {
        // Penting untuk ditulis agar datastore tidak null
        `when`(githubUserUseCase.getThemeSetting()).thenReturn(flowOf(false))
        mainViewModel = MainViewModel(githubUserUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when Get Github User List Should Not Null and Success`() = runTest {
        val expectedGithubUsers = flowOf(Resource.Success(dummyGithubUsers))

        `when`(githubUserUseCase.getAllGithubUser("kevin"))
            .thenReturn(expectedGithubUsers)

        mainViewModel.getAllGithubUser("kevin")
        val actualGithubUsers = mainViewModel.githubUser.getOrAwaitValue()
        assertNotNull(actualGithubUsers)
        assertTrue(actualGithubUsers is Resource.Success)
        assertEquals(dummyGithubUsers.size, (actualGithubUsers as Resource.Success).data?.size)
    }
}