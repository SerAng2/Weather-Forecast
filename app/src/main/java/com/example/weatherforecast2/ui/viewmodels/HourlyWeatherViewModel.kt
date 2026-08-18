package com.example.weatherforecast2.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast2.domain.interactor.HourlyWeatherInteractor
import com.example.weatherforecast2.domain.model.WeatherState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HourlyWeatherViewModel(
    private val interactor: HourlyWeatherInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val uiState: StateFlow<WeatherState> = _uiState

    fun getHourlyWeather(id: String) {
        viewModelScope.launch {
            interactor.getHourlyWeather(id).collect { state ->
                _uiState.value = state

            }
        }
    }
}
