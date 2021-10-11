package com.ngocpv.data.source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ngocpv.data.entity.WeatherInformationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("select * from WeatherInformationEntity ")
    fun getAllWeatherInformation() : List<WeatherInformationEntity>

    @Query("select * from WeatherInformationEntity where name like '%' || :cityName || '%' limit 1")
    fun findByCityName(cityName : String) : WeatherInformationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weatherInformationEntity: WeatherInformationEntity)
}