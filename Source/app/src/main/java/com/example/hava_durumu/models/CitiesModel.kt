package com.example.hava_durumu.models

data class CitiesModel(
    val distance: Int,
    val latt_long: String,
    val location_type: String,
    val title: String,
    val woeid: Int
)