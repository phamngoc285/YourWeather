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

        fun toDomainEntity(): WeatherInformation {
                return WeatherInformation(
                        city.toDomainEntity(),
                        forecasts?.map{ it.toDomainEntity()} ?: listOf()
                )
        }

//        fun toDAOEntity() = WeatherInformationEntity(
//                name,
//                weather.firstOrNull()?.main ?: "",
//                weather.firstOrNull()?.description ?: "",
//                base,
//                main.temp,
//                main.pressure,
//                main.humidity,
//                main.temp_min,
//                main.temp_max,
//                visibility,
//                wind.speed,
//                wind.deg
//        )
}

data class Forecast(
        @SerializedName("dt")
        val dateTime : Long,
        val weather : List<Weather>,
        val pressure : Int,
        @SerializedName("temp")
        val temperature: Temperature,
        val humidity : Int,
) {
        fun toDomainEntity() = WeatherInformation.Forecast(
                DatetimeUtil.getDateTime(dateTime) ?: "",
                weather.first().toDomainEntity(),
                pressure,
                temperature.toDomainEntity(),
                humidity
        )
}

data class City(
        val name : String
) {
        fun toDomainEntity() = WeatherInformation.City(name)
}

data class Weather(
        val main: String,
        val description: String
){
        fun toDomainEntity() = WeatherInformation.Weather(main, description)
}

data class Temperature(
        val min: Float,
        val max : Float
) {
        fun toDomainEntity() = WeatherInformation.Temperature(min, max)
}