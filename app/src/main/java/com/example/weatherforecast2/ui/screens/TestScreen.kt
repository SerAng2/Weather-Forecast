package com.example.weatherforecast2.ui.screens

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherforecast2.R

// Убедитесь, что импорт size работает — если не работает, используйте Modifier.width() и height()
// import androidx.compose.foundation.layout.size (если ошибка — используйте Modifier.width(24.dp).height(24.dp))

@Composable
fun TestScreen() {
    Icon(
        painter = painterResource(id = R.drawable.vector_12),
        contentDescription = "Дополнительная иконка",
    )
}

@Preview(showBackground = true)
@Composable
fun TestScreen_Preview() {
    TestScreen()
}

