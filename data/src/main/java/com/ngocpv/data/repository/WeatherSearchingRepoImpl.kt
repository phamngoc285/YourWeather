package com.ngocpv.data.repository

import android.util.Log
import com.ngocpv.data.BaseRepositoryImp
import com.ngocpv.data.entity.WeatherInformationEntity
import com.ngocpv.data.model.SearchWeatherResponse
import com.ngocpv.data.source.DataService
import com.ngocpv.data.source.database.WeatherDao
import com.ngocpv.domain.entity.WeatherInformation
import com.ngocpv.domain.repository.ResponseHandler
import com.ngocpv.domain.repository.WeatherSearchingRepo
import com.ngocpv.domain.usercase.SearchWeatherUseCase.Companion.LOCAL_RESULT_VALID_DURATION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherSearchingRepoImpl(
            private val weatherDao: WeatherDao,
            private val service: DataService
        ) : BaseRepositoryImp() , WeatherSearchingRepo {
    override suspend fun searchWeatherFromCityName(cityName: String): ResponseHandler<WeatherInformation> {
        return callAPI(weatherDao) {
            service.searchWeather(cityName)
        }
    }

    override fun getLocalWeatherFromCityName(cityName: String): WeatherInformation? {
        val localData = weatherDao.findByCityName(cityName)?.toDomainModel()
        Log.d("ngocpv1", "local $localData")
        return if(localData != null
                    && (System.currentTimeMillis() - localData.timeStamp) > LOCAL_RESULT_VALID_DURATION)
            localData
        else
            null

    }

    private suspend inline fun callAPI(weatherDao: WeatherDao, crossinline apiToCall: suspend ()  -> SearchWeatherResponse) : ResponseHandler<WeatherInformation>{
        return try {
            val time = System.currentTimeMillis()
            val response = withContext(Dispatchers.IO) {
                apiToCall()
            }
            Log.d("ngocpv1", "remote result ${System.currentTimeMillis() - time} ")
            when(response.cod){
                200 -> {
                    weatherDao.insertWeather(response.toDAOEntity())
                    ResponseHandler.Success(response.toDomainEntity())
                }
                else -> ResponseHandler.Failure(response.message)
            }
        } catch (ex : Exception){
            Log.d("ngocpv", "ex $ex")
            ex.printStackTrace()
            ResponseHandler.Failure("Something wrong! Try again. $ex")
        }
    }
}