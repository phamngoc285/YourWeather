package com.ngocpv.domain.repository

import com.ngocpv.domain.entity.WeatherInformation

interface WeatherSearchingRepo {
    suspend fun searchWeatherFromCityName(cityName : String) : ResponseHandler<WeatherInformation>

    /**
     * @return local valid result weather information for city name </br>
     * The valid result is the one gotten from server no longer than [SearchWeatherUseCase.LOCAL_RESULT_VALID_DURATION] compare with current time.
     */
    fun getLocalWeatherFromCityName(cityName: String) : WeatherInformation?

    /**
     * remove outdated local data </br>
     * Outdated data is the one gotten from server longer than [SearchWeatherUseCase.LOCAL_RESULT_VALID_DURATION] compare with current time.
     */
    fun refineLocalData()
}