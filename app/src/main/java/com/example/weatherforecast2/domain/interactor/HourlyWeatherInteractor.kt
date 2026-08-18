package com.example.weatherforecast2.domain.interactor

import com.example.weatherforecast2.domain.model.WeatherState
import kotlinx.coroutines.flow.Flow

interface HourlyWeatherInteractor {
    suspend fun getHourlyWeather(location: String) : Flow<WeatherState>
}
