package com.example.weatherforecast2.di

import com.example.weatherforecast2.data.impl.GetWeatherDataRepositoryImpl
import com.example.weatherforecast2.data.impl.HourlyWeatherRepositoryImpl
import com.example.weatherforecast2.data.impl.SearchRepositoryImpl
import com.example.weatherforecast2.domain.repository.GetWeatherDataRepository
import com.example.weatherforecast2.domain.repository.HourlyWeatherRepository
import com.example.weatherforecast2.domain.repository.SearchRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<SearchRepository> {
        SearchRepositoryImpl()
    }

    single<GetWeatherDataRepository> {
        GetWeatherDataRepositoryImpl()
    }

    single<HourlyWeatherRepository> {
        HourlyWeatherRepositoryImpl()
    }
}

