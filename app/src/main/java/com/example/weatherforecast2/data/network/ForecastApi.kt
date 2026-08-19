package com.example.weatherforecast2.data.network

import com.example.weatherforecast2.data.dto.ForecastAuthRequest
import com.example.weatherforecast2.data.dto.ForecastAuthResponse
import com.example.weatherforecast2.data.dto.ForecastResponse
import com.example.weatherforecast2.data.dto.HourlyWeatherResponse
import com.example.weatherforecast2.data.dto.LocationsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ForecastApi {
    @POST("/authorize/token?expire_hours=-1")
    fun authenticate(
        @Body request: ForecastAuthRequest
    ): Call<ForecastAuthResponse>

    @GET("/api/v1/location/search/{query}?lang=ru")
    fun getLocations(
        @Header("Authorization") token: String, @Path("query") query: String
    ): Call<LocationsResponse>

    @GET("/api/v1/current/{location}")
    fun getForecast(
        @Header("Authorization") token: String, @Path("location") locationId: String?
    ): Call<ForecastResponse>

    @GET("/api/v1/forecast/hourly/{location}")
    suspend fun getHourlyWeather(
        @Header("Authorization") token: String, @Path("location") location: String
    ): Response<HourlyWeatherResponse>
}
