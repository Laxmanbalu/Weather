package com.demo.openweatherapp.domain.model

/**
 *
 */
data class WeatherModel(
    val weather: Weather,
    val main: Main,
    val wind: Wind
)

data class Weather(
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int,
    val sea_level: Int = 0,
    val grnd_level: Int = 0
)

data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double
)