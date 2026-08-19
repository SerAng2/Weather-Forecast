package com.example.weatherforecast2.di

import com.example.weatherforecast2.ui.viewmodels.HourlyWeatherViewModel
import com.example.weatherforecast2.ui.viewmodels.SearchViewModel
import com.example.weatherforecast2.ui.viewmodels.WeatherDataViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SearchViewModel(get()) }
    viewModel { WeatherDataViewModel(get()) }
    viewModel { HourlyWeatherViewModel(get()) }
}
