package com.example.weatherforecast2.ui.navigation

sealed class Destination(val route: String) {

    data object SearchScreen : Destination(ROUTE_FIRST)
    data object WeatherDataScreen : Destination(ROUTE_SECOND)
    companion object {
        private const val ROUTE_FIRST = "route_first"
        private const val ROUTE_SECOND = "route_second"
    }
}
