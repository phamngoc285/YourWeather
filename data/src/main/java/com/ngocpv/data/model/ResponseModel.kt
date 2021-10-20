package com.ngocpv.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ngocpv.data.entity.WeatherInformationEntity
import com.ngocpv.data.utils.DatetimeUtil
import com.ngocpv.domain.entity.WeatherInformation

abstract class BaseResponse(
        @SerializedName("cod")
        @Expose
        var cod : Int  = 0,
        @SerializedName("message")
        @Expose
        var message : String = ""
)

class ErrorResponse constructor(cod: Int, message: String): BaseResponse(cod, message)

data class SearchWeatherResponse (
        val city : City,
        @SerializedName("list")
        val forecasts : List<Forecast>?
) : BaseResponse() {
        data class Forecast(
                @SerializedName("dt")
                val dateTime: Long,
                val weather: List<Weather>,
                val pressure: Int,
                @SerializedName("temp")
                val temperature: Temperature,
                val humidity: Int,
        )

        data class City(
                val name: String
        )

        data class Weather(
                val main: String,
                val description: String
        )

        data class Temperature(
                val min: Float,
                val max: Float
        )
}