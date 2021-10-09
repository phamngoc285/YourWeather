package com.ngocpv.domain.usercase

import com.ngocpv.domain.entity.WeatherCondition
import com.ngocpv.domain.repository.ResponseHandler
import com.ngocpv.domain.repository.WeatherSearchingRepo

class SearchWeatherUseCase(private val weatherSearchingRepo : WeatherSearchingRepo) : BaseUseCase<String, ResponseHandler<WeatherCondition>>() {

    override suspend fun invoke(cityName: String): ResponseHandler<WeatherCondition> {
        return weatherSearchingRepo.searchWeatherFromCityName(cityName)
    }
}