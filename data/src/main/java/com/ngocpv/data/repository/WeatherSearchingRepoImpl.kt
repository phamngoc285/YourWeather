package com.ngocpv.data.repository

import com.ngocpv.domain.repository.ResponseHandler
import com.ngocpv.domain.repository.WeatherSearchingRepo

class WeatherSearchingRepoImpl : WeatherSearchingRepo {
    override fun searchWeatherFromCityName(cityName: String): ResponseHandler<String> {
        TODO("Not yet implemented")
    }
}