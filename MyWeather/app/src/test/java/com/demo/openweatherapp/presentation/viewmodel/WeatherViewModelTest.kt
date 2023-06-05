package com.demo.openweatherapp.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.demo.openweatherapp.MainCoroutineRule
import com.demo.openweatherapp.domain.model.WeatherModel
import com.demo.openweatherapp.domain.usecase.WeatherUseCase
import com.demo.openweatherapp.presentation.BaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito

class WeatherViewModelTest : BaseTest() {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var weatherUseCase: WeatherUseCase

    private lateinit var viewModel: WeatherViewModel

    @Before
    override fun setup() {
        super.setup()
        viewModel = WeatherViewModel(weatherUseCase)
    }

    @Test
    fun testFetchWeatherData_success() =
        runTest {
            val mockWeatherModel = Mockito.mock(WeatherModel::class.java)
            Mockito.`when`(weatherUseCase.getWeatherData(Mockito.anyString())).thenReturn(
                mockWeatherModel
            )
            viewModel.fetchWeatherData(
                Mockito.anyString()
            )
            Assert.assertNotNull(viewModel.weatherData.value)
        }

    @Test
    fun testFetchWeatherData_failure() =
        runTest {
            Mockito.`when`(weatherUseCase.getWeatherData(Mockito.anyString())).thenReturn(
                null
            )
            viewModel.fetchWeatherData(
                Mockito.anyString()
            )
            Assert.assertNull(viewModel.weatherData.value)
        }
}