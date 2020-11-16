package com.example.hava_durumu.api

import com.example.hava_durumu.models.CitiesListModel
import com.example.hava_durumu.models.CitiesDailyForecastModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {
    @GET("/api/location/search/")
    suspend fun getNearByCitiesByLocation(
        @Query("lattlong") latLong: String
    ): Response<CitiesListModel>

    @GET("/api/location/{woeid}")
    suspend fun getCitiesDailyForecastDetail(
        @Path("woeid") woeid: Int
    ): Response<CitiesDailyForecastModel>
}