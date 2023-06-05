package com.demo.openweatherapp.domain.repo

import com.demo.openweatherapp.domain.model.WeatherModel

interface WeatherRepository {
    suspend fun getWeatherData(cityName: String): WeatherModel
}