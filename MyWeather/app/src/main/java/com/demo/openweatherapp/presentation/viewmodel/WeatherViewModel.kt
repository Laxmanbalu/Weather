package com.demo.openweatherapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.openweatherapp.domain.model.WeatherModel
import com.demo.openweatherapp.domain.usecase.WeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 */
@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherUseCase: WeatherUseCase) :
    ViewModel() {
    private val _weatherData = MutableLiveData<WeatherModel>()
    val weatherData: LiveData<WeatherModel> = _weatherData

    /**
     * Method to fetch Weather Info
     * @param cityName : City
     */
    fun fetchWeatherData(cityName: String) {
        viewModelScope.launch {
            try {
                val data = weatherUseCase.getWeatherData(cityName)
                _weatherData.value = data
            } catch (e: Exception) {
                _weatherData.value = null
            }
        }
    }
}
