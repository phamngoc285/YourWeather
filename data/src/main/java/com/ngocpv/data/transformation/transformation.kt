package com.ngocpv.data.transformation

import com.ngocpv.data.entity.WeatherInformationEntity
import com.ngocpv.data.model.SearchWeatherResponse
import com.ngocpv.data.utils.DatetimeUtil
import com.ngocpv.domain.entity.WeatherInformation

//region from SearchWeatherResponse
fun SearchWeatherResponse.toDomainEntity(): WeatherInformation {
    return WeatherInformation(
        city.toDomainEntity(),
        forecasts?.map{ it.toDomainEntity()} ?: listOf()
    )
}

fun SearchWeatherResponse.toDAOEntity() = WeatherInformationEntity(
    city.name,
    forecasts?.map{ it.toDAOEntity()} ?: listOf()
)

fun SearchWeatherResponse.Forecast.toDomainEntity() = WeatherInformation.Forecast(
    DatetimeUtil.getDateTime(dateTime) ?: "",
    weather.first().toDomainEntity(),
    pressure,
    temperature.toDomainEntity(),
    humidity
)

fun SearchWeatherResponse.Forecast.toDAOEntity() = WeatherInformationEntity.Forecast(
    DatetimeUtil.getDateTime(dateTime) ?: "",
    weather.first().toDAOEntity(),
    pressure,
    temperature.toDAOEntity(),
    humidity
)

fun SearchWeatherResponse.City.toDomainEntity() = WeatherInformation.City(name)

fun SearchWeatherResponse.Weather.toDomainEntity() = WeatherInformation.Weather(main, description)

fun SearchWeatherResponse.Weather.toDAOEntity() = WeatherInformationEntity.Weather(main, description)

fun SearchWeatherResponse.Temperature.toDomainEntity() = WeatherInformation.Temperature(min, max)

fun SearchWeatherResponse.Temperature.toDAOEntity() = WeatherInformationEntity.Temperature(min, max)
//endregion

//region from WeatherInformationEntity DAO entity
fun WeatherInformationEntity.toDomainModel() = WeatherInformation(
    WeatherInformation.City(name),
    forecasts.map { WeatherInformation.Forecast(
        it.dateTime,
        it.weather.toDomainModel(),
        it.pressure,
        it.temperature.toDomainModel(),
        it.humidity)
    },
    fetchedTime
)

fun WeatherInformationEntity.Weather.toDomainModel() = WeatherInformation.Weather(
    main,
    description
)

fun WeatherInformationEntity.Temperature.toDomainModel() = WeatherInformation.Temperature(
    min,
    max
)
//endregion