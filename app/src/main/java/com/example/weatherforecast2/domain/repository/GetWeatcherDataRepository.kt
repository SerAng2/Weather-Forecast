package com.example.weatherforecast2.domain.repository

import com.example.weatherforecast2.domain.model.ForecastLocation
import com.example.weatherforecast2.domain.model.WeatherState
import kotlinx.coroutines.flow.Flow

interface GetWeatherDataRepository {
    suspend fun getWeatherData(location: ForecastLocation?) : Flow<WeatherState>
}
