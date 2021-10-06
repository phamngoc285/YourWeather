package com.ngocpv.domain.repository

interface WeatherSearchingRepo {
    fun searchWeatherFromCityName(cityName : String) : ResponseHandler<String>
}