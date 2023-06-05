
package com.demo.openweatherapp.domain.usecase

import com.demo.openweatherapp.domain.model.WeatherModel
import com.demo.openweatherapp.domain.repo.WeatherRepository
import javax.inject.Inject

/**
 *
 */
class WeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend fun getWeatherData(cityName: String): WeatherModel {
        return weatherRepository.getWeatherData(cityName)
    }
}