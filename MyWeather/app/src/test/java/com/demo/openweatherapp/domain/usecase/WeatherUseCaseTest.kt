package com.demo.openweatherapp.domain.usecase

import com.demo.openweatherapp.domain.model.WeatherModel
import com.demo.openweatherapp.domain.repo.WeatherRepository
import com.demo.openweatherapp.presentation.BaseTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class WeatherUseCaseTest : BaseTest() {
    @Mock
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var weatherUseCase: WeatherUseCase


    @Before
    override fun setup() {
        super.setup()
        weatherUseCase = WeatherUseCase(weatherRepository)
    }

    @Test
    fun testFetchWeatherData_success() =
        runTest {
            val mockWeatherModel = Mockito.mock(WeatherModel::class.java)
            Mockito.`when`(weatherRepository.getWeatherData(CITY)).thenReturn(
                mockWeatherModel
            )
            val result = weatherUseCase.getWeatherData(CITY)
            assertNotNull(result)
        }

    @Test
    fun testFetchWeatherData_failure() =
        runTest {
            Mockito.`when`(weatherRepository.getWeatherData(CITY)).thenReturn(
                null
            )
            val result = weatherUseCase.getWeatherData(CITY)
            assertNull(result)
        }

    private companion object {
        const val CITY = "Edison"
    }
}