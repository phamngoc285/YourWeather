package com.ngocpv.data.source

import com.ngocpv.data.model.SearchWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DataService {
    @GET("weather?")
    suspend fun searchWeather(@Query("q") cityName : String) : SearchWeatherResponse
}