package com.example.weatherforecast2.domain.model

sealed class WeatherState {
    object Loading : WeatherState()
    data class Success(
        val temperature: String,
        val feelsLikeTemp: String,
        val windSpeed: String,
        val time: String,
        val relHumidity: String,
        val symbolPhrase: String,
        val location: ForecastLocation?
    ) : WeatherState()

    data class Error(val message: String) : WeatherState()

    data class HourlyWeatherSuccess(
        val weather: List<HourlyWeather>
    ) : WeatherState()
}
