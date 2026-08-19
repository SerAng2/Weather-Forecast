package com.example.weatherforecast2.domain.interactor

import com.example.weatherforecast2.domain.model.ForecastLocation

interface SearchInteractor {
    fun performSearch(query: String, onResult: (List<ForecastLocation>) -> Unit)
}
