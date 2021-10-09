package com.ngocpv.data.source

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val DOMAIN = "https://api.openweathermap.org/data/2.5/"
const val API_KEY = "032a8b34f5159465fb7d1948dce58e07"

fun getDataService() : DataService{
    return Retrofit.Builder()
        .baseUrl(DOMAIN)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DataService::class.java)
}