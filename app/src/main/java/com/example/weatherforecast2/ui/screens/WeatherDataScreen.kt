package com.example.weatherforecast2.ui.screens

import android.R.attr.text
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.weatherforecast2.R
import com.example.weatherforecast2.domain.model.ForecastLocation
import com.example.weatherforecast2.domain.model.WeatherState
import com.example.weatherforecast2.ui.viewmodels.HourlyWeatherViewModel

@Composable
fun WeatherDataScreen(
    id: String,
    navHostController: NavHostController,
    hourlyWeatherViewModel: HourlyWeatherViewModel
) {

    val idInt = id.toIntOrNull() ?: return

    LaunchedEffect(idInt) {
        hourlyWeatherViewModel.getHourlyWeather(id)
    }


    val uiState by hourlyWeatherViewModel.uiState.collectAsStateWithLifecycle()
    var isSelected by remember { mutableStateOf(false) }

    // Анимация для плавного изменения цвета
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Color.LightGray.copy(alpha = 0.5f) else Color.Transparent,
        label = "backgroundColorAnimation"
    )
    val shadowElevation by animateDpAsState(
        targetValue = if (isSelected) 8.dp else 0.dp,
        label = "shadowElevationAnimation"
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colorResource(R.color.custom_sky_blue)
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            IconButton(
                onClick = { navHostController.popBackStack() },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Назад",
                    tint = Color.White
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.vector_12),
                contentDescription = "Дополнительная иконка",
                modifier = Modifier.size(164.dp) // Всегда задавай явный размер!
            )

            when (uiState) {
                is WeatherState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(24.dp))
                }

                is WeatherState.Success -> {
                    val weather = uiState as WeatherState.Success

                    Text(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 30.dp, top = 46.dp),
                        text = "Сегодня",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium,
                        fontSize = 24.sp,
                    )

                    Text(
                        text = formatWeatherDate(weather.time),
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 54.dp, end = 30.dp)
                    )
                }


                    is WeatherState.HourlyWeatherSuccess -> {
                        val weather = uiState as WeatherState.HourlyWeatherSuccess


                        // Основной Column с эффектом нажатия
                        LazyRow(
                            modifier = Modifier,
//                        contentPadding = PaddingValues(12.dp)
                        ) {
                            for (hourly in weather.weather) {
                                items(1) { // хардкод 24
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier
                                            .padding(start = 8.dp, top = 110.dp)
                                            .width(64.dp)
                                            .background(backgroundColor, RoundedCornerShape(18.dp))
                                            .clickable(
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = LocalIndication.current,
                                                onClick = { isSelected = !isSelected }
                                            )
                                    ) {
                                        Text(
                                            modifier = Modifier.padding(top = 12.dp),
                                            text = "${hourly.temperature.toInt()}°",
                                            color = Color.White,
                                            fontSize = 18.sp
                                        )

                                        Icon(
                                            modifier = Modifier.padding(top = 22.dp),
                                            painter = painterResource(id = R.drawable.image_group_650),
                                            contentDescription = "Плейсхолдер"
                                        )

                                        Text(
                                            modifier = Modifier.padding(top = 6.dp),
                                            text = formatTime(hourly.time),
                                            color = Color.White,
                                            fontSize = 18.sp
                                        )
                                    }
                                }
                            }
                        }

                    Text(
                        modifier = Modifier.padding(start = 16.dp, top = 446.dp),
                        text = "Следующий прогноз",
                        color = Color.White
                    )
                }

                is WeatherState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${uiState as WeatherState.Error}.message",
                            color = Color.Red,
                            fontSize = 20.sp
                        )
                    }
                }

                else -> {}
            }
        }
    }
}

fun formatWeatherDate(isoString: String): String {
    // Ожидаемый формат: 2021-12-20T09:28+02:00
    // Нам нужны части: год-месяц-день

    val parts = isoString.split("-")
    if (parts.size < 3) return isoString // Защита от битых данных

    val month = parts[1].toInt()
    val day = parts[2].substringBefore("T") // Убираем время

    // Массив названий месяцев на русском (индекс 0 = январь, поэтому сдвиг на 1)
    val monthsRu = arrayOf(
        "Янв", "Фев", "Мар", "Апр", "Май", "Июн",
        "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"
    )

    return "${monthsRu[month - 1]}, $day"
}

fun formatTime(isoTime: String): String {
    // Например, "2024-06-21T15:00+03:00" -> "15:00"
    val parts = isoTime.split("T")
    if (parts.size < 2) return isoTime
    val timePart = parts[1].split("+")[0] // убираем timezone
    return timePart
}

@Composable
@Preview(showBackground = true)
fun ForecastScreenPreview() {

}
