package com.example.weatherforecast2.domain.interactor

import com.example.weatherforecast2.domain.model.WeatherState
import com.example.weatherforecast2.domain.repository.HourlyWeatherRepository
import kotlinx.coroutines.flow.Flow

class HourlyWeatherInteractorImpl(
    private val repository: HourlyWeatherRepository
) : HourlyWeatherInteractor {
    override suspend fun getHourlyWeather(location: String): Flow<WeatherState> {
        return repository.getHourlyWeather(location)
    }
}
