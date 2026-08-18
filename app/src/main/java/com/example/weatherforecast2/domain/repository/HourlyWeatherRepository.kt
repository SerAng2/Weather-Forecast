package com.example.weatherforecast2.domain.repository

import com.example.weatherforecast2.domain.model.WeatherState
import kotlinx.coroutines.flow.Flow

interface HourlyWeatherRepository {
    suspend fun getHourlyWeather(location: String) : Flow<WeatherState>
}
