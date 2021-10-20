package com.ngocpv.data.source.remote

import com.ngocpv.data.source.remote.DataService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val DOMAIN = "https://api.openweathermap.org/data/2.5/"
const val API_KEY = "60c6fbeb4b93ac653c492ba806fc346d"
const val METRIC = "metric"
const val NUMBERS_OF_FORECAST_DAY = "7"

fun provideClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val url = it.request().url()
                .newBuilder()
                .addQueryParameter("appid", API_KEY)
                .addQueryParameter("units", METRIC)
                .addQueryParameter("cnt", NUMBERS_OF_FORECAST_DAY)
                .build()
            val newRequest = it.request()
                .newBuilder()
                .url(url)
                .build()
            it.proceed(newRequest)
        }.build()

fun getDataService(okHttpClient: OkHttpClient) : DataService {
    return Retrofit.Builder()
        .baseUrl(DOMAIN)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(DataService::class.java)
}