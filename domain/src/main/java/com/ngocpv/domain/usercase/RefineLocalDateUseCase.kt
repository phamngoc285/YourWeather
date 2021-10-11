package com.ngocpv.domain.usercase

import com.ngocpv.domain.repository.ResponseHandler
import com.ngocpv.domain.repository.WeatherSearchingRepo

class RefineLocalDateUseCase(private val weatherSearchingRepo : WeatherSearchingRepo) : BaseUseCaseNoParamNoResult(){
    override suspend fun invoke() {
        weatherSearchingRepo.refineLocalData()
    }
}