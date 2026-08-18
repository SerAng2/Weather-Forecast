package com.example.weatherforecast2.data.mapper

import com.example.weatherforecast2.data.dto.HourlyWeatherResponse
import com.example.weatherforecast2.domain.model.HourlyWeather


class HourlyWeatherMapper {
    fun mapToDomain(response: HourlyWeatherResponse): List<HourlyWeather> {
        return response.hourly.map { hourlyDto ->
            HourlyWeather(
                time = hourlyDto.time,
                temperature = hourlyDto.temperature,
            )
        }.ifEmpty {
            throw IllegalStateException("Empty hourly weather data received")
        }
    }
}
