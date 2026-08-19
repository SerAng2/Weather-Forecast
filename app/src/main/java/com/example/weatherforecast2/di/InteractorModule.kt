package com.example.weatherforecast2.di

import com.example.weatherforecast2.domain.interactor.GetWeatherDataInteractor
import com.example.weatherforecast2.domain.interactor.GetWeatherDataInteractorImpl
import com.example.weatherforecast2.domain.interactor.HourlyWeatherInteractor
import com.example.weatherforecast2.domain.interactor.HourlyWeatherInteractorImpl
import com.example.weatherforecast2.domain.interactor.SearchInteractor
import com.example.weatherforecast2.domain.interactor.SearchInteractorImpl
import org.koin.dsl.module

val interactorModule = module {
    single<SearchInteractor> {
        SearchInteractorImpl(get())
    }

    single<GetWeatherDataInteractor> {
        GetWeatherDataInteractorImpl(get())
    }

    single<HourlyWeatherInteractor> {
        HourlyWeatherInteractorImpl(get())
    }
}
