package com.ngocpv.data.source

import com.ngocpv.data.model.SearchWeatherResponse
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Query

interface DataService {
    @GET("weather?")
    suspend fun searchWeather(@Query("q") cityName : String, @Query("appid") apiKey : String ) : SearchWeatherResponse

    @GET("weather?")
    suspend fun test(@Query("q") cityName : String, @Query("appid") apiKey : String ) : JSONObject
}