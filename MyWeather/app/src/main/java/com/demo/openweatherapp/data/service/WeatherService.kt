package com.demo.openweatherapp.data.service

import com.demo.openweatherapp.data.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/weather?&apiKey=e8aba0e69d46853276d18734072aacba")
    suspend fun getWeatherData(
    @Query("q")  query : String
    ): Response<WeatherResponse>
}
