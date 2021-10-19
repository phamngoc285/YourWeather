package com.ngocpv.yourweather.transformation

import com.ngocpv.domain.entity.WeatherInformation
import com.ngocpv.yourweather.model.Temperature
import com.ngocpv.yourweather.model.Weather
import com.ngocpv.yourweather.model.WeatherInformationUIModel

fun WeatherInformation.Forecast.toUIModel() = WeatherInformationUIModel.Forecast(
    dateTime,
    weather.toUIModel(),
    pressure,
    temperature.toUIModel(),
    humidity
)

fun WeatherInformation.Weather.toUIModel() = Weather(main, description)

fun WeatherInformation.Temperature.toUIModel() = Temperature(min + max / 2)