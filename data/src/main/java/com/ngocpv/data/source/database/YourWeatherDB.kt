package com.ngocpv.data.source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ngocpv.data.entity.WeatherInformationEntity

@Database(
    entities = [WeatherInformationEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(value = [com.ngocpv.data.source.database.TypeConverter::class])
abstract class YourWeatherDB : RoomDatabase() {
    abstract fun provideWeatherDAO() : WeatherDao
}

class TypeConverter{
    @TypeConverter
    fun fromListOfForecast(forecasts : List<WeatherInformationEntity.Forecast>) : String? {
        return Gson().toJson(forecasts)
    }

    @TypeConverter
    fun toListOfForecast(forecasts: String) : List<WeatherInformationEntity.Forecast>? {
        val listType = object : TypeToken<ArrayList<WeatherInformationEntity.Forecast>>(){}.type
        return Gson().fromJson(forecasts, listType)
    }
}