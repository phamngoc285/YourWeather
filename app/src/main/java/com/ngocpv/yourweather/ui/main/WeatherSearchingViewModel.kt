package com.ngocpv.yourweather.ui.main

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import com.ngocpv.domain.repository.ResponseHandler
import com.ngocpv.domain.usercase.RefineLocalDateUseCase
import com.ngocpv.domain.usercase.SearchWeatherUseCase
import com.ngocpv.yourweather.coroutinecontextprovider.CoroutineContextProvider
import com.ngocpv.yourweather.model.WeatherInformationUIModel
import com.ngocpv.yourweather.transformation.toUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherSearchingViewModel(
        private val searchWeatherUseCase: SearchWeatherUseCase,
        private val refineLocalDateUseCase: RefineLocalDateUseCase,
        private val coroutineContextProvider: CoroutineContextProvider
    ) : ViewModel() {

    //region livedata
    val weatherForecastLiveData : LiveData<List<WeatherInformationUIModel>>
        get() = _weatherForecastLiveData

    private val _weatherForecastLiveData = MutableLiveData<List<WeatherInformationUIModel>>()
    //endregion

    //region search weather forecast use case
    fun searchWeather(cityName : String){
        val validation = validateInputSearchingWeather(cityName)
        if(!validation.first) {
            _weatherForecastLiveData.postValue(listOf(WeatherInformationUIModel.Error(validation.second)))
            return
        }

        viewModelScope.launch(coroutineContextProvider.IO) {
            searchWeatherUseCase.invoke(cityName).collect { it ->
                when(it){
                    is ResponseHandler.Loading -> {
                        _weatherForecastLiveData.postValue(listOf(WeatherInformationUIModel.Loading))
                    }
                    is ResponseHandler.Failure -> {
                        _weatherForecastLiveData.postValue(listOf(WeatherInformationUIModel.Error(it.error)))
                    }
                    is ResponseHandler.Success -> {
                        _weatherForecastLiveData.postValue(it.data.forecasts.map { item -> item.toUIModel() })
                    }
                }
            }
        }
    }

    /**
     * Validate input for city name.
     * @return a pair of Boolean and String <br>
     * First value is whether input is valid <br>
     * Second value is the error string (if any)
     */
    @VisibleForTesting
    fun validateInputSearchingWeather(input : String?) : Pair<Boolean, String> {
        return when {
            input.isNullOrBlank() -> Pair (false, "Please enter a city name")
            input.length < MINIMUM_SEARCH_INPUT_CHARACTERS -> Pair(false, "City name must be longer than ${MINIMUM_SEARCH_INPUT_CHARACTERS - 1} characters")
            else -> Pair(true, "")
        }
    }

    //endregion

    //region refine local cache use case
    fun onViewResumed(){
        viewModelScope.launch(coroutineContextProvider.IO) {
            refineLocalDateUseCase.invoke()
        }
    }
    //endregion

    companion object {
        const val MINIMUM_SEARCH_INPUT_CHARACTERS = 4
    }
}