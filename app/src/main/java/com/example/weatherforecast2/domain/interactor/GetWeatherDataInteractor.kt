package com.example.weatherforecast2.domain.interactor


import com.example.weatherforecast2.domain.model.ForecastLocation
import com.example.weatherforecast2.domain.model.WeatherState
import kotlinx.coroutines.flow.Flow

interface GetWeatherDataInteractor {
    suspend fun getWeatherData(location: ForecastLocation?) : Flow<WeatherState>
}
