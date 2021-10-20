package com.ngocpv.domain.repository

import com.ngocpv.domain.entity.WeatherInformation

interface WeatherSearchingRepo {
    suspend fun searchWeatherFromCityName(cityName : String) : ResponseHandler<WeatherInformation>

    /**
     * @param upToDateDuration duration when a data remains up to date,
     * counted from the time it was fetched from remote
     * @return local valid result weather information for city name from DB</br>
     */
    fun getDBWeatherFromCityName(cityName: String, upToDateDuration : Int) : WeatherInformation?

    /**
     * @param upToDateDuration duration when a data remains up to date,
     * counted from the time it was fetched from remote
     * @return in-mem up to date result weather information for city name</br>
     */
    fun getInMemWeatherFromCityName(cityName: String, upToDateDuration : Int) : WeatherInformation?

    /**
     * remove outdated local data </br>
     * Outdated data is the one gotten from server longer than [SearchWeatherUseCase.LOCAL_RESULT_VALID_DURATION] compare with current time.
     */
    fun refineLocalData()
}