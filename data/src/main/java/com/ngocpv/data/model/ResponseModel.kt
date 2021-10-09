package com.ngocpv.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ngocpv.domain.entity.BaseDomainEntity
import com.ngocpv.domain.entity.WeatherCondition

abstract class BaseResponse

data class SearchWeatherResponse (
        @SerializedName("cod")
        @Expose
        var cod : Int  = 0,
        @SerializedName("message")
        @Expose
        var message : String = "",
        @SerializedName("weather")
        @Expose
        val weather : List<Weather>,
        @SerializedName("base")
        @Expose
        val base : String,
        @SerializedName("main")
        @Expose
        val main : Main,
        @SerializedName("visibility")
        @Expose
        val visibility : Int,
        @SerializedName("wind")
        @Expose
        val wind : Wind,
        @SerializedName("name")
        @Expose
        val name : String
        ) : BaseResponse() {

        fun toDomainEntity(): WeatherCondition {
                return WeatherCondition(
                        weather.toDomainEntity().first(),
                        base,
                        main.toDomainEntity(),
                        visibility,
                        wind.toDomainEntity(),
                        name
                )
        }
}

fun List<Weather>.toDomainEntity() : List<com.ngocpv.domain.entity.Weather>{
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
        fun toDomainEntity() = com.ngocpv.domain.entity.Weather(main, description)
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
        fun toDomainEntity() = com.ngocpv.domain.entity.Main(temp, pressure, humidity, temp_min, temp_max)
}

data class Wind(
        @SerializedName("speed")
        @Expose
        val speed : Float,
        @SerializedName("deg")
        @Expose
        val deg : Float
){
        fun toDomainEntity() = com.ngocpv.domain.entity.Wind(speed, deg)
}