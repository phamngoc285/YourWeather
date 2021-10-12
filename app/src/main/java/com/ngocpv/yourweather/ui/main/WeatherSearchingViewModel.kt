package com.ngocpv.yourweather.ui.main

import androidx.lifecycle.*
import com.ngocpv.domain.entity.WeatherInformation
import com.ngocpv.domain.repository.ResponseHandler
import com.ngocpv.domain.usercase.RefineLocalDateUseCase
import com.ngocpv.domain.usercase.SearchWeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherSearchingViewModel(
        private val searchWeatherUseCase: SearchWeatherUseCase,
        private val refineLocalDateUseCase: RefineLocalDateUseCase
    ) : ViewModel() {

    val resultLiveData : LiveData<ResponseHandler<WeatherInformation>>
        get() = _resultLiveData

    private val _resultLiveData = MutableLiveData<ResponseHandler<WeatherInformation>>()

    fun searchWeather(cityName : String){
        val validation = validateInputSearchingWeather(cityName)
        if(validation.first) {
            _resultLiveData.value = ResponseHandler.Failure(validation.second)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val result = searchWeatherUseCase.invoke(cityName)
            result.collect {
                withContext(Dispatchers.Main){
                    _resultLiveData.value = it
                }
            }
        }
    }

    fun onViewResumed(){
        viewModelScope.launch(Dispatchers.IO) {
            refineLocalDateUseCase.invoke()
        }

        viewModelScope
    }

    /**
     * Validate input for city name.
     * @return a pair of Boolean and String </br>
     * First value is whether input is valid </br>
     * Second value is the error string if any
     */
    private fun validateInputSearchingWeather(input : String?) : Pair<Boolean, String> {
        return when {
            input.isNullOrBlank() -> Pair (true, "Please enter a city name")
            input.length <= 3 -> Pair(true, "City name must be longer than 3 characters")
            else -> Pair(false, "")
        }
    }
}