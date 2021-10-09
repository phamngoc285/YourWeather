package com.ngocpv.yourweather.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngocpv.domain.entity.WeatherCondition
import com.ngocpv.domain.repository.ResponseHandler
import com.ngocpv.domain.usercase.SearchWeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherSearchingViewModel(private val searchWeatherUseCase: SearchWeatherUseCase) : ViewModel() {

    val resultLiveData : LiveData<ResponseHandler<WeatherCondition>>
        get() = _resultLiveData

    private val _resultLiveData = MutableLiveData<ResponseHandler<WeatherCondition>>()

    fun searchWeather(cityName : String){
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            val result = searchWeatherUseCase.invoke(cityName)
            Log.d("ngocpv", "vm result $result")
            withContext(Dispatchers.Main){
                _resultLiveData.value = result
            }
        }
        _resultLiveData.value = ResponseHandler.Loading
    }
}