package com.example.weatherforecast2.domain.interactor

import com.example.weatherforecast2.domain.model.ForecastLocation
import com.example.weatherforecast2.domain.model.WeatherState
import com.example.weatherforecast2.domain.repository.GetWeatherDataRepository
import kotlinx.coroutines.flow.Flow


class GetWeatherDataInteractorImpl(
    val repository: GetWeatherDataRepository
) : GetWeatherDataInteractor {
    override suspend fun getWeatherData(location: ForecastLocation?): Flow<WeatherState> {
        return repository.getWeatherData(location)
    }
}
