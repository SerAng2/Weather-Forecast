package com.example.weatherforecast2.data.dto

data class CurrentWeatherDto(
    val temperature: String,
    val feelsLikeTemp: String,
    val windSpeed: String,
    val time: String,
    val relHumidity: String,
    val symbolPhrase: String
)
