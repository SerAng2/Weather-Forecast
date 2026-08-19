package com.example.weatherforecast2.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast2.domain.interactor.GetWeatherDataInteractor
import com.example.weatherforecast2.domain.model.ForecastLocation
import com.example.weatherforecast2.domain.model.WeatherState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


open class WeatherDataViewModel(
    private val interactor: GetWeatherDataInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val uiState: StateFlow<WeatherState> = _uiState

    fun getWeatherData(id: ForecastLocation) {
        viewModelScope.launch {
            interactor.getWeatherData(id).collect { state ->
                _uiState.value = state
            }
        }
    }
}
