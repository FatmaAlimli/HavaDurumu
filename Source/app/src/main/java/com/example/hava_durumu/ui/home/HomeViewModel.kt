package com.example.hava_durumu.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hava_durumu.api.GenericResult
import com.example.hava_durumu.models.CitiesListModel
import com.example.hava_durumu.repository.WeatherRepository
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(private val weatherRepository: WeatherRepository) : ViewModel() {

    private val weatherCitiesMutableLiveData = MutableLiveData<GenericResult<CitiesListModel?>>()

    val weatherCitiesLiveData: LiveData<GenericResult<CitiesListModel?>>
        get() = weatherCitiesMutableLiveData


    fun getNearByCitiesByLocation(lat: String, long: String) {
        weatherCitiesMutableLiveData.value = GenericResult.Loading()
        viewModelScope.launch {
            weatherRepository.getNearByCitiesByLocation(lat, long).let { response ->
                if (response.isSuccessful) {
                    weatherCitiesMutableLiveData.value = GenericResult.Success(response.body())
                } else {
                    weatherCitiesMutableLiveData.value = GenericResult.Failure(response.errorBody())
                }
            }
        }
    }
}