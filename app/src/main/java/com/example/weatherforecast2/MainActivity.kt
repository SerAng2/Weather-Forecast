package com.example.weatherforecast2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.weatherforecast2.domain.interactor.SearchInteractor
import com.example.weatherforecast2.ui.navigation.AppNavHost
import com.example.weatherforecast2.ui.screens.SearchScreen
import com.example.weatherforecast2.ui.viewmodels.HourlyWeatherViewModel
import com.example.weatherforecast2.ui.viewmodels.SearchViewModel
import com.example.weatherforecast2.ui.viewmodels.WeatherDataViewModel
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent.inject

class MainActivity : ComponentActivity() {

    // Инициализируем SearchInteractor с помощью Koin
    private val viewModel: SearchViewModel by inject()
    private val hourlyWeatherViewModel: HourlyWeatherViewModel by inject()
    private val weatherDataViewModel: WeatherDataViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val statusBarColor = ContextCompat.getColor(this, R.color.custom_sky_blue)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                statusBarColor,  // Голубой цвет для статус-бара
                Color.Transparent.toArgb()  // Для тёмной темы
            ),
            navigationBarStyle = SystemBarStyle.auto(
                Color.Transparent.toArgb(),
                Color.Transparent.toArgb()
            )
        )

        setContent {

            Column(modifier = Modifier.padding(top = 30.dp)) {
                val navController = rememberNavController()
                AppNavHost(
                    navHostController = navController,
                    viewModel = viewModel,
                    hourlyWeatherViewModel = hourlyWeatherViewModel
                )
            }
        }
    }
}
