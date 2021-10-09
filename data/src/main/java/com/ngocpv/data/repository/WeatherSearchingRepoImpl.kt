package com.ngocpv.data.repository

import android.util.Log
import com.ngocpv.data.BaseRepositoryImp
import com.ngocpv.data.model.SearchWeatherResponse
import com.ngocpv.data.source.DataService
import com.ngocpv.domain.entity.WeatherCondition
import com.ngocpv.domain.repository.ResponseHandler
import com.ngocpv.domain.repository.WeatherSearchingRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherSearchingRepoImpl(private val service: DataService) : BaseRepositoryImp() , WeatherSearchingRepo {
    override suspend fun searchWeatherFromCityName(cityName: String): ResponseHandler<WeatherCondition> {
        return callAPI {
            val result = service.searchWeather(cityName)
            Log.d("ngocpv1", "result $result")
            result
        }
    }

    private suspend inline fun callAPI(crossinline apiToCall: suspend ()  -> SearchWeatherResponse) : ResponseHandler<WeatherCondition>{
        return try {
            val response = withContext(Dispatchers.IO) {
                apiToCall()
            }
            when(response.cod){
                200 -> ResponseHandler.Success(response.toDomainEntity())
                else -> ResponseHandler.Failure(response.message)
            }
        } catch (ex : Exception){
            Log.d("ngocpv", "ex $ex")
            ex.printStackTrace()
            ResponseHandler.Failure("Something wrong! Try again. $ex")
        }
    }
}