package com.ngocpv.data.source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ngocpv.data.entity.WeatherInformationEntity

@Database(
    entities = [WeatherInformationEntity::class],
    version = 1,
    exportSchema = true
)
abstract class YourWeatherDB : RoomDatabase() {
    abstract fun provideWeatherDAO() : WeatherDao
}