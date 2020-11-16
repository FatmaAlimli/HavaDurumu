package com.example.hava_durumu.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hava_durumu.api.GenericResult
import com.example.hava_durumu.models.CitiesDailyForecastModel
import com.example.hava_durumu.repository.WeatherRepository
import kotlinx.coroutines.launch

class DetailViewModel @ViewModelInject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    private val citiesDetailMutableLiveData =
        MutableLiveData<GenericResult<CitiesDailyForecastModel?>>()

    val citiesDetailLiveData: LiveData<GenericResult<CitiesDailyForecastModel?>>
        get() = citiesDetailMutableLiveData

    fun getCitiesDailyForecastDetail(woeid: Int) {
        citiesDetailMutableLiveData.value = GenericResult.Loading()
        viewModelScope.launch {
            weatherRepository.getCitiesDailyForecastDetail(woeid).let { response ->
                if (response.isSuccessful) {
                    citiesDetailMutableLiveData.value = GenericResult.Success(response.body())
                } else {
                    citiesDetailMutableLiveData.value =
                        GenericResult.Failure(response.errorBody())
                }
            }
        }
    }
}