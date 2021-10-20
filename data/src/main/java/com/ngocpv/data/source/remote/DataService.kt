package com.ngocpv.data.source.remote

import com.ngocpv.data.model.SearchWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DataService {
    @GET("forecast/daily?")
    suspend fun searchWeather(@Query("q") cityName : String) : SearchWeatherResponse
}