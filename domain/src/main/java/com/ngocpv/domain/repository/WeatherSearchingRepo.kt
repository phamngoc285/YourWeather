package com.ngocpv.domain.repository

import com.ngocpv.domain.entity.WeatherCondition

interface WeatherSearchingRepo {
    suspend fun searchWeatherFromCityName(cityName : String) : ResponseHandler<WeatherCondition>
}