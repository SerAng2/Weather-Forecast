package com.example.weatherforecast2.data.impl

import com.example.weatherforecast2.BuildConfig
import com.example.weatherforecast2.data.network.RetrofitNetworkClient
import com.example.weatherforecast2.domain.model.ForecastLocation
import com.example.weatherforecast2.domain.model.WeatherState
import com.example.weatherforecast2.domain.repository.GetWeatherDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.awaitResponse

class GetWeatherDataRepositoryImpl : GetWeatherDataRepository {
    private val token = BuildConfig.SERVER_KEY

    override suspend fun getWeatherData(location: ForecastLocation?): Flow<WeatherState> = flow {
        emit(WeatherState.Loading)

        try {
            val response = RetrofitNetworkClient.retrofit.getForecast(
                "Bearer $token",
                location?.id
            ).awaitResponse()

            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                val state = WeatherState.Success(
                    location = location,
                    temperature = body.current.temperature,
                    windSpeed = "${body.current.windSpeed} км/ч",
                    time = body.current.time,
                    relHumidity = "${body.current.relHumidity} %",
                    symbolPhrase = body.current.symbolPhrase,
                    feelsLikeTemp = body.current.feelsLikeTemp
                )
                emit(state)
            } else {
                emit(WeatherState.Error("Ошибка получения данных: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(WeatherState.Error("Ошибка: ${e.message}"))
        }
    }
}
