package com.example.weatherforecast2.domain.repository

import com.example.weatherforecast2.domain.model.ForecastLocation

interface SearchRepository {
    fun performSearch(query: String, onResult: (List<ForecastLocation>) -> Unit)
}
