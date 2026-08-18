package com.example.weatherforecast2.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

object RetrofitNetworkClient {
    val retrofit: ForecastApi = Retrofit.Builder()
        .baseUrl("https://fnw-us.foreca.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ForecastApi::class.java)
}