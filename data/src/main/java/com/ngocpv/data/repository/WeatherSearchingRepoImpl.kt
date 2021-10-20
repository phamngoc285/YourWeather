package com.ngocpv.data.repository

import android.util.Log
import com.ngocpv.data.model.BaseResponse
import com.ngocpv.data.model.ErrorResponse
import com.ngocpv.data.model.SearchWeatherResponse
import com.ngocpv.data.source.MemCachingUtil
import com.ngocpv.data.source.remote.DataService
import com.ngocpv.data.source.database.WeatherDao
import com.ngocpv.data.transformation.toDAOEntity
import com.ngocpv.data.transformation.toDomainEntity
import com.ngocpv.data.transformation.toDomainModel
import com.ngocpv.domain.entity.WeatherInformation
import com.ngocpv.domain.repository.ResponseHandler
import com.ngocpv.domain.repository.WeatherSearchingRepo
import com.ngocpv.domain.usercase.SearchWeatherUseCase.Companion.LOCAL_RESULT_VALID_DURATION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class WeatherSearchingRepoImpl(
            private val weatherDao: WeatherDao,
            private val service: DataService
        ) : BaseRepositoryImp() , WeatherSearchingRepo {
    override suspend fun searchWeatherFromCityName(cityName: String): ResponseHandler<WeatherInformation> {
        return requestData(weatherDao, cityName)
    }

    override fun getDBWeatherFromCityName(cityName: String, upToDateDuration : Int): WeatherInformation? {
        return weatherDao.findByCityName(cityName, System.currentTimeMillis(), upToDateDuration)?.toDomainModel()?.also {
            MemCachingUtil.push(it)
        }
    }

    override fun getInMemWeatherFromCityName(cityName: String, upToDateDuration: Int) = MemCachingUtil.get(cityName, upToDateDuration)

    override fun refineLocalData() {
        weatherDao.deleteOutdatedWeatherInformation(System.currentTimeMillis(), LOCAL_RESULT_VALID_DURATION)
    }

    private suspend fun requestData(weatherDao: WeatherDao, cityName: String) : ResponseHandler<WeatherInformation>{
        return try {
            val response = withContext(Dispatchers.IO) {
                callAPI(cityName)
            }
            when(response){
                is SearchWeatherResponse -> {
                    weatherDao.insertWeather(response.toDAOEntity().also {
                        it.fetchedTime = System.currentTimeMillis()
                    })
                    ResponseHandler.Success(response.toDomainEntity().also {
                        it.fetchedTime = System.currentTimeMillis()
                        MemCachingUtil.push(it)
                    })
                }
                else -> ResponseHandler.Failure(response.message)
            }
        } catch (ex : HttpException){
            ex.printStackTrace()
            ResponseHandler.Failure(ex.message())
        } catch (ex : Exception){
            ex.printStackTrace()
            ResponseHandler.Failure("Something wrong! Try again")
        }
    }

    private suspend fun callAPI(cityName: String) : BaseResponse {
        return try{
            service.searchWeather(cityName)
        } catch (ex : HttpException){
            ex.printStackTrace()
            ErrorResponse(ex.code(), ex.message())
        }
    }
}