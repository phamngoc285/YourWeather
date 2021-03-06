package com.ngocpv.data.source.database

import androidx.room.*
import com.ngocpv.data.entity.WeatherInformationEntity

@Dao
interface WeatherDao {
    @Query("select * from WeatherInformationEntity ")
    fun getAllWeatherInformation() : List<WeatherInformationEntity>

    @Query("select * from WeatherInformationEntity where name like '%' || :cityName || '%' and :currentTimestamp - fetchedTime <= :upToDateDuration limit 1")
    fun findByCityName(cityName : String, currentTimestamp: Long, upToDateDuration: Int) : WeatherInformationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weatherInformationEntity: WeatherInformationEntity)

    @Query("delete from WeatherInformationEntity where :currentTimestamp - fetchedTime > :validDuration")
    fun deleteOutdatedWeatherInformation(currentTimestamp: Long, validDuration: Int)
}