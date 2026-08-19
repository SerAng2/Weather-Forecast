package com.example.weatherforecast2.data.dto

import com.google.gson.annotations.SerializedName

data class HourlyWeatherDto(
    @SerializedName("temperature") val temperature: Double,
    @SerializedName("time") val time: String
)
