package com.example.weatherforecast2.data.dto

import com.google.gson.annotations.SerializedName

class HourlyWeatherResponse(
    @SerializedName("forecast") val hourly: List<HourlyWeatherDto>
)
