package com.ngocpv.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ngocpv.data.entity.WeatherInformationEntity
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
        @SerializedName("weather")
        @Expose
        val weather : List<Weather> = listOf(),
        @SerializedName("base")
        @Expose
        val base : String = "",
        @SerializedName("main")
        @Expose
        val main : Main = Main(0f,0,0,0f,0f),
        @SerializedName("visibility")
        @Expose
        val visibility : Int = 0,
        @SerializedName("wind")
        @Expose
        val wind : Wind = Wind(0f,0f),
        @SerializedName("name")
        @Expose
        val name : String = ""
        ) : BaseResponse() {

        fun toDomainEntity(): WeatherInformation {
                return WeatherInformation(
                        weather.toDomainEntity().first(),
                        base,
                        main.toDomainEntity(),
                        visibility,
                        wind.toDomainEntity(),
                        name
                )
        }

        fun toDAOEntity() = WeatherInformationEntity(
                name,
                weather.firstOrNull()?.main ?: "",
                weather.firstOrNull()?.description ?: "",
                base,
                main.temp,
                main.pressure,
                main.humidity,
                main.temp_min,
                main.temp_max,
                visibility,
                wind.speed,
                wind.deg
        )
}

fun List<Weather>.toDomainEntity() : List<WeatherInformation.Weather>{
        return this.map { it.toDomainEntity() }
}

data class Weather(
        @SerializedName("main")
        @Expose
        val main : String,
        @SerializedName("description")
        @Expose
        val description : String
){
        fun toDomainEntity() = WeatherInformation.Weather(main, description)
}

data class Main(
        @SerializedName("temp")
        @Expose
        val temp : Float,
        @SerializedName("pressure")
        @Expose
        val pressure : Int,
        @SerializedName("humidity")
        @Expose
        val humidity : Int,
        @SerializedName("temp_min")
        @Expose
        val temp_min : Float,
        @SerializedName("temp_max")
        @Expose
        val temp_max : Float
){
        fun toDomainEntity() = WeatherInformation.Main(temp, pressure, humidity, temp_min, temp_max)
}

data class Wind(
        @SerializedName("speed")
        @Expose
        val speed : Float,
        @SerializedName("deg")
        @Expose
        val deg : Float
){
        fun toDomainEntity() = WeatherInformation.Wind(speed, deg)
}