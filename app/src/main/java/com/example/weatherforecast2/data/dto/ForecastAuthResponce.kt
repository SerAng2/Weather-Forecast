package com.example.weatherforecast2.data.dto

import com.google.gson.annotations.SerializedName

class ForecastAuthResponse(@SerializedName("access_token") val token: String)
