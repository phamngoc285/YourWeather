package com.ngocpv.yourweather.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.ngocpv.domain.entity.WeatherInformation
import com.ngocpv.domain.repository.ResponseHandler
import com.ngocpv.domain.usercase.SearchWeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherSearchingViewModel(private val searchWeatherUseCase: SearchWeatherUseCase) : ViewModel() {

    val resultLiveData : LiveData<ResponseHandler<WeatherInformation>>
        get() = _resultLiveData

    private val _resultLiveData = MutableLiveData<ResponseHandler<WeatherInformation>>()

    fun searchWeather(cityName : String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = searchWeatherUseCase.invoke(cityName)
            result.collect {
                withContext(Dispatchers.Main){
                    Log.d("ngocpv1", "vm result $it")
                    _resultLiveData.value = it
                }
            }
        }
    }
}