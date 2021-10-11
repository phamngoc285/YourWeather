package com.ngocpv.domain.repository

import com.ngocpv.domain.entity.WeatherInformation

interface WeatherSearchingRepo {
    suspend fun searchWeatherFromCityName(cityName : String) : ResponseHandler<WeatherInformation>

    fun getLocalWeatherFromCityName(cityName: String) : WeatherInformation?
}