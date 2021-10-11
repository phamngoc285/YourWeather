package com.ngocpv.domain.usercase

import com.ngocpv.domain.entity.WeatherInformation
import com.ngocpv.domain.repository.ResponseHandler
import com.ngocpv.domain.repository.WeatherSearchingRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchWeatherUseCase(private val weatherSearchingRepo : WeatherSearchingRepo) : BaseUseCase<String, Flow<ResponseHandler<WeatherInformation>>>() {

    override suspend fun invoke(cityName: String): Flow<ResponseHandler<WeatherInformation>> = flow {
        emit(ResponseHandler.Loading)

        val localResult = weatherSearchingRepo.getLocalWeatherFromCityName(cityName)
        if(localResult != null)
            emit(ResponseHandler.Success(localResult))
        else
            emit(weatherSearchingRepo.searchWeatherFromCityName(cityName))
    }

    companion object{
        const val LOCAL_RESULT_VALID_DURATION = 43200000 // 12 hours
    }
}