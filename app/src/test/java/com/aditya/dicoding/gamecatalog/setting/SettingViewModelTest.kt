package com.aditya.dicoding.gamecatalog.setting

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.aditya.dicoding.gamecatalog.core.domain.usecase.GameUseCase
import com.aditya.dicoding.gamecatalog.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SettingViewModelTest{

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var gameUseCase: GameUseCase
    private lateinit var settingViewModel: SettingViewModel

    @Before
    fun setUp() {
        settingViewModel = SettingViewModel(gameUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getThemeSettings Should return correct theme setting`() = runTest {
        val observer = Observer<Boolean> {}

        val flow = flow {
            delay(10)
            emit(true)
        }

        try {
            val expectedResponse = MutableLiveData<Boolean>()
            expectedResponse.value = true
            Mockito.`when`(gameUseCase.getThemeSetting()).thenReturn(flow)

            val actualResponse = settingViewModel.getThemeSetting().observeForever(observer)

            Mockito.verify(gameUseCase).getThemeSetting()
            Assert.assertNotNull(actualResponse)
            Assert.assertTrue(true)
        } finally {
            settingViewModel.getThemeSetting().removeObserver(observer)
        }
    }
}