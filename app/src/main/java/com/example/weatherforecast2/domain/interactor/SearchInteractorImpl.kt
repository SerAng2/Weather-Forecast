package com.example.weatherforecast2.domain.interactor

import com.example.weatherforecast2.domain.model.ForecastLocation
import com.example.weatherforecast2.domain.repository.SearchRepository

class SearchInteractorImpl(private val repository: SearchRepository) : SearchInteractor {
    override fun performSearch(
        query: String,
        onResult: (List<ForecastLocation>) -> Unit
    ) {
        return repository.performSearch(query, onResult)
    }
}
