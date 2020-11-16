package com.example.hava_durumu.repository

import com.example.hava_durumu.api.WeatherApi
import com.example.hava_durumu.models.CitiesListModel
import com.example.hava_durumu.models.CitiesDailyForecastModel
import retrofit2.Response
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi) {
    suspend fun getNearByCitiesByLocation(lat: String, long: String): Response<CitiesListModel> {
        return weatherApi.getNearByCitiesByLocation("${lat},${long}")
    }

    suspend fun getCitiesDailyForecastDetail(woeid: Int): Response<CitiesDailyForecastModel> {
        return weatherApi.getCitiesDailyForecastDetail(woeid)
    }
}