package com.ngocpv.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ngocpv.domain.entity.WeatherInformation

@Entity
data class WeatherInformationEntity(
    @PrimaryKey val name : String,
    val forecasts : List<Forecast>,
    var fetchedTime : Long = 0
    ) {

    data class Forecast(
        val dateTime : String,
        val weather : Weather,
        val pressure : Int,
        val temperature: Temperature,
        val humidity : Int,
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