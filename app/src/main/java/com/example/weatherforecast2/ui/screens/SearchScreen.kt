package com.example.weatherforecast2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherforecast2.R
import com.example.weatherforecast2.ui.navigation.Destination
import com.example.weatherforecast2.ui.viewmodels.SearchViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    viewModel: SearchViewModel ,
    modifier: Modifier = Modifier,
    onCitySelected: (String) -> Unit
) {

    val searchResults by viewModel.searchResalt.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    //  var searchText by remember { mutableStateOf("") }

    var text by rememberSaveable { mutableStateOf("") }

    var progress by remember { mutableStateOf(0.0f) }
    val scope = rememberCoroutineScope()

    val focusRequester: FocusRequester = remember { FocusRequester() }
    var hasFocus by rememberSaveable { mutableStateOf(false) }

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(R.color.custom_sky_blue),
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                )
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {

            Column {
                TextField(
                    value = searchText, // Теперь берём из ViewModel
                    onValueChange = { newValue ->
                        viewModel.performSearch(newValue)
                    },
                    placeholder = { Text("Поиск") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .onFocusChanged { hasFocus = it.hasFocus}
                        .clickable(onClick = {
                            if (progress >= 1f) progress = 0f
                            scope.launch {
                                while (progress < 1f) {
                                    progress += 0.1f
                                    delay(1000L)
                                }
                            }
                        })
                        .background(
                            Color.Transparent,
                            shape = RoundedCornerShape(20.dp)
                        ),

                    // .padding(vertical = 30.dp, horizontal = 16.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
                CircularProgressIndicator(
                    progress = progress,
                    color = Color.Black
                )
                // Отображаем список найденных городов
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp), // Небольшой отступ сверху списка
                ) {
                    items(searchResults) { forecastLocation ->
                        // Здесь вы можете отобразить элемент списка, например, как Text
                        // Замените .name на актуальное поле, содержащее имя города
                        forecastLocation.name?.let {
                            Text(
                                text = it, // Предполагая, что ForecastLocation имеет поле 'name'
                                fontSize = 23.sp,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .padding(vertical = 4.dp, horizontal = 8.dp)
                                    .clickable(onClick = {
                                        // Передаём id города в onCitySelected
                                        onCitySelected(forecastLocation.id)
                                    })
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreen() {
}
