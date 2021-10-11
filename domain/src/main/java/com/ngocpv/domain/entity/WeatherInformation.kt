package com.ngocpv.domain.entity

data class WeatherInformation (
    val weather : Weather,
    val base : String,
    val main : Main,
    val visibility : Int,
    val wind : Wind,
    val name : String,
    val timeStamp : Long = 0
) : BaseDomainEntity() {

    data class Weather(
        val main: String,
        val description: String
    )

    data class Main(
        val temp: Float,
        val pressure: Int,
        val humidity: Int,
        val temp_min: Float,
        val temp_max: Float
    )

    data class Wind(
        val speed: Float,
        val deg: Float
    )
}
