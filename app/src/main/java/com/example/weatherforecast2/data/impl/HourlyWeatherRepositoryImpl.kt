package com.example.weatherforecast2.data.impl

import android.annotation.SuppressLint
import android.util.Log
import com.example.weatherforecast2.BuildConfig
import com.example.weatherforecast2.data.mapper.HourlyWeatherMapper
import com.example.weatherforecast2.data.network.RetrofitNetworkClient
import com.example.weatherforecast2.domain.model.WeatherState
import com.example.weatherforecast2.domain.repository.HourlyWeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.google.gson.Gson

class HourlyWeatherRepositoryImpl : HourlyWeatherRepository {

    private val token = BuildConfig.SERVER_KEY

    @SuppressLint("SuspiciousIndentation")
    override suspend fun getHourlyWeather(location: String): Flow<WeatherState> = flow {
        emit(WeatherState.Loading)
        try {
            val response = RetrofitNetworkClient.retrofit.getHourlyWeather(
                token = "Bearer $token",
                location = location
            )

            Log.d("API_DEBUG", "Raw response: ${response.raw()}")  // Логируем сырые данные

            if (response.isSuccessful) {
                val body = response.body()
                Log.d("API_DEBUG", "Parsed response: $body")
            } else {
                Log.e("API_ERROR", "Error code: ${response.code()}, message: ${response.message()}")
            }

            // Извлечение тела ответа с обработкой null
            val body = response.body()
            if (body == null) {
                Log.e("API_ERROR", "Response body is null!")
                emit(WeatherState.Error("Server returned empty response"))
                return@flow
            }

            // Логирование сырых данных для отладки
            Log.d("API_DEBUG", "Raw response: ${Gson().toJson(body)}")


            Log.w("API_WARNING", "No hourly weather data received")



            if (!response.isSuccessful) {
                val errorBody = response.errorBody()?.string()
                Log.e("API_ERROR", "HTTP ${response.code()}: $errorBody")
                emit(WeatherState.Error("Server error: ${errorBody ?: "Unknown reason"}"))
            }
            // Маппинг с обработкой ошибок
            val hourlyWeather = try {
                HourlyWeatherMapper().mapToDomain(body)
            } catch (e: Exception) {
                Log.e("MAPPING_ERROR", "Failed to map hourly weather: ${e.message}")
                throw e
            }

            emit(WeatherState.HourlyWeatherSuccess(hourlyWeather))
            Log.d("API_DEBUG", "Successfully processed hourly weather for $location")

        } catch (e: Exception) {
            Log.e("API_ERROR", "Exception: ${e.message}", e)
            emit(WeatherState.Error("Failed to fetch hourly weather: ${e.message ?: "Unknown error"}"))
        }
    }
}
