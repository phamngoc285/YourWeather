package com.ngocpv.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ngocpv.domain.entity.WeatherInformation

@Entity
data class WeatherInformationEntity(
    @PrimaryKey val name : String,
    val weatherMain : String,
    val weatherDescription : String,
    val base : String,
    val mainTemp : Float,
    val mainPressure : Int,
    val mainHumidity : Int,
    val mainTempMin : Float,
    val mainTempMax : Float,
    val visibility : Int,
    val windSpeed : Float,
    val windDeg : Float,
    val timestamp : Long = 0,
    ) {

    fun toDomainModel() = WeatherInformation(
        WeatherInformation.Weather(weatherMain, weatherDescription),
        base,
        WeatherInformation.Main(mainTemp, mainPressure, mainHumidity, mainTempMin, mainTempMax),
        visibility,
        WeatherInformation.Wind(windSpeed, windDeg),
        name,
        timestamp
    )
}