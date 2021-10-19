package com.ngocpv.domain.entity

data class WeatherInformation (
    val city : City,
    val forecasts : List<Forecast>,
    val timeStamp : Long = 0
) : BaseDomainEntity() {

    data class Forecast(
        val dateTime : String,
        val weather : Weather,
        val pressure : Int,
        val temperature: Temperature,
        val humidity : Int,
    )

    data class City(
        val name : String
    )

    data class Weather(
        val main: String,
        val description: String
    )

    data class Temperature(
        val min: Float,
        val max : Float,

    )
}
