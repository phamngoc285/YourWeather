package com.ngocpv.yourweather.model

sealed class WeatherInformationUIModel {
    data class Forecast(
        val dateTime : String,
        val weather : Weather,
        val pressure : Int,
        val temperature: Temperature,
        val humidity : Int,
    ) : WeatherInformationUIModel()

    object Loading : WeatherInformationUIModel()

    class Error(var message: String) : WeatherInformationUIModel()
}

data class Weather(
    val main: String,
    val description: String
)

data class Temperature(
    val average : Float
)
