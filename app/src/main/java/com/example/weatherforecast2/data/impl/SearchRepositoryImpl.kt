package com.example.weatherforecast2.data.impl

import com.example.weatherforecast2.data.dto.ForecastAuthRequest
import com.example.weatherforecast2.data.dto.ForecastAuthResponse
import com.example.weatherforecast2.data.dto.LocationsResponse
import com.example.weatherforecast2.data.mapper.ForecastLocationMapper
import com.example.weatherforecast2.data.network.RetrofitNetworkClient
import com.example.weatherforecast2.domain.model.ForecastLocation
import com.example.weatherforecast2.domain.repository.SearchRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRepositoryImpl() : SearchRepository {
    
    private var token = BuildConfig.SERVER_KEY
    private val mapper = ForecastLocationMapper()

    override fun performSearch(query: String, onResult: (List<ForecastLocation>) -> Unit) {
        RetrofitNetworkClient.retrofit.getLocations("Bearer $token", query)
            .enqueue(object : Callback<LocationsResponse> {
                override fun onResponse(
                    call: Call<LocationsResponse>,
                    response: Response<LocationsResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            val foundLocations =
                                response.body()?.locations?.map { mapper.mapToDomain(it) }
                                    ?: emptyList()
                            onResult(foundLocations)
                        }

                        401 -> {
                            authenticate { success ->
                                if (success) {
                                    performSearch(
                                        query,
                                        onResult
                                    )
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<LocationsResponse>, t: Throwable) {
                    onResult(emptyList())
                }
            })
    }

    private fun authenticate(onComplete: (Boolean) -> Unit) {
        RetrofitNetworkClient.retrofit.authenticate(
            ForecastAuthRequest(
            user = BuildConfig.SERVER_USERNAME,
                password = BuildConfig.SERVER_PASSWORD
            )
        ).enqueue(object : Callback<ForecastAuthResponse> {
            override fun onResponse(
                call: Call<ForecastAuthResponse>,
                response: Response<ForecastAuthResponse>
            ) {
                token = response.body()?.token ?: token
                onComplete(response.isSuccessful)
            }

            override fun onFailure(call: Call<ForecastAuthResponse>, t: Throwable) {
                onComplete(false)
            }
        })
    }
}
