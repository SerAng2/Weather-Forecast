package com.example.weatherforecast2.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast2.domain.interactor.SearchInteractor
import com.example.weatherforecast2.domain.model.ForecastLocation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchInteractor: SearchInteractor
) : ViewModel() {

    private val _searchResalt = MutableStateFlow<List<ForecastLocation>>(emptyList())
    val searchResalt: StateFlow<List<ForecastLocation>> = _searchResalt.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    fun performSearch(query: String) {
        if (query.isEmpty()) {
            _searchResalt.value = emptyList()
            _searchText.value = query
            return
        }

        _searchText.value = query
        _isLoading.value = true
        viewModelScope.launch {
            try {
                searchInteractor.performSearch(query) { resalts ->
                    _searchResalt.value = resalts
                }
            } catch (e: Exception) {
                _searchResalt.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
