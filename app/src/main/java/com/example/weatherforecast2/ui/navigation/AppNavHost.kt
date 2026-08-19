package com.example.weatherforecast2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.weatherforecast2.ui.screens.SearchScreen
import com.example.weatherforecast2.ui.screens.WeatherDataScreen
import com.example.weatherforecast2.ui.viewmodels.HourlyWeatherViewModel
import com.example.weatherforecast2.ui.viewmodels.SearchViewModel
import com.example.weatherforecast2.ui.viewmodels.WeatherDataViewModel

@Composable
fun AppNavHost(
    navHostController: NavHostController,
    viewModel: SearchViewModel,
    hourlyWeatherViewModel: HourlyWeatherViewModel
) {
    NavHost(
        navController = navHostController,
        startDestination = Destination.SearchScreen.route
    ) {
        composable(route = Destination.SearchScreen.route) {
            SearchScreen(
                viewModel = viewModel,
                modifier = Modifier,
                onCitySelected = { id ->
                    // ✅ ПРАВИЛЬНО: Формируем строку маршрута для ForecastScreen + / + id
                    navHostController.navigate("${Destination. WeatherDataScreen.route}/$id")
                })
        }

        composable(
            route = "${Destination.WeatherDataScreen.route}/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            // ✅ ПРАВИЛЬНО: Берем аргумент из backStackEntry
            val id = backStackEntry.arguments?.getString("id")
            // Если тип Int, используй: .getInt("id")

            WeatherDataScreen(id = id ?: "", navHostController, hourlyWeatherViewModel)
        }
    }
}
