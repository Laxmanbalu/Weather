package com.demo.openweatherapp.data.repoimpl

import com.demo.openweatherapp.data.model.WeatherResponse
import com.demo.openweatherapp.data.service.WeatherService
import com.demo.openweatherapp.domain.model.Main
import com.demo.openweatherapp.domain.model.Weather
import com.demo.openweatherapp.domain.model.WeatherModel
import com.demo.openweatherapp.domain.model.Wind
import com.demo.openweatherapp.domain.repo.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val weatherService: WeatherService) :
    WeatherRepository {
    override suspend fun getWeatherData(cityName: String): WeatherModel {
        val response = weatherService.getWeatherData(cityName)

        if (response.isSuccessful) {
            val weatherResponse = response.body()
            // Convert the WeatherResponse to WeatherModel object
            return weatherResponse?.toWeatherModel() ?: throw Exception("Invalid response")
        } else {
            throw Exception("Failed to fetch weather data: ${response.code()}")
        }
    }

    private fun WeatherResponse.toWeatherModel(): WeatherModel {
        val localWeather = weather.firstOrNull()
        // Perform the necessary conversion from WeatherResponse to WeatherModel
        return WeatherModel(
            weather = Weather(
                main = localWeather?.main.orEmpty(),
                description = localWeather?.description.orEmpty(),
                icon = localWeather?.icon.orEmpty()
            ),
            main = Main(
                temp = main.temp,
                feels_like = main.feels_like,
                temp_min = main.temp_min,
                temp_max = main.temp_max,
                pressure = main.pressure,
                humidity = main.humidity
            ),
            wind = Wind(
                wind.speed,
                wind.deg,
                wind.gust
            )
        )
    }
}
