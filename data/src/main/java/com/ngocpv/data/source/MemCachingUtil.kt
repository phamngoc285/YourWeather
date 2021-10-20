package com.ngocpv.data.source

import com.ngocpv.domain.entity.WeatherInformation

object MemCachingUtil {
    private val weatherInformationList = HashMap<String, WeatherInformation>()

    /**
     * @param upToDateDuration duration when a data remains up to date,
     * counted from the time it was fetched from remote
     * @return up-to-date data for city name
     */
    fun get(cityName : String, upToDateDuration : Int) : WeatherInformation? {
        val key = weatherInformationList.keys.firstOrNull{ item -> item.lowercase().contains(cityName)}
            ?: return null
        val result = weatherInformationList[key] ?: return null
        return if(System.currentTimeMillis() - result.fetchedTime <= upToDateDuration)
            result
        else
            null
    }

    fun push(weatherInformation: WeatherInformation){
        weatherInformationList[weatherInformation.city.name] = weatherInformation
    }
}