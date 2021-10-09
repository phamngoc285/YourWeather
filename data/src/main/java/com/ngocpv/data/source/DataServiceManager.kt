package com.ngocpv.data.source

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val DOMAIN = "https://api.openweathermap.org/data/2.5/"
const val API_KEY = "032a8b34f5159465fb7d1948dce58e07"

fun provideClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val url = it.request().url()
                .newBuilder()
                .addQueryParameter("appid", API_KEY)
                .build()
            val newRequest = it.request()
                .newBuilder()
                .url(url)
                .build()
            it.proceed(newRequest)
        }.build()

fun getDataService(okHttpClient: OkHttpClient) : DataService{
    return Retrofit.Builder()
        .baseUrl(DOMAIN)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(DataService::class.java)
}