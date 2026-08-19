package com.example.weatherforecast2.data.mapper

import com.example.weatherforecast2.data.dto.ForecastLocationDto
import com.example.weatherforecast2.domain.model.ForecastLocation

class ForecastLocationMapper {
    fun mapToDomain(location: ForecastLocationDto): ForecastLocation {
        return ForecastLocation(
            id = location.id,
            name = location.name,
            country = location.country
        )
    }
}
