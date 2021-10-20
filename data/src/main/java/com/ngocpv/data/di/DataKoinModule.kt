package com.ngocpv.data.di

import androidx.room.Room
import com.ngocpv.data.repository.WeatherSearchingRepoImpl
import com.ngocpv.data.source.database.YourWeatherDB
import com.ngocpv.data.source.remote.getDataService
import com.ngocpv.data.source.remote.provideClient
import com.ngocpv.domain.repository.WeatherSearchingRepo
import org.koin.core.module.Module
import org.koin.dsl.module

val dataModule = module {
    single {
            Room.databaseBuilder(get(), YourWeatherDB::class.java, "Your-Weather-DB")
                .build()
    }

    single { get<YourWeatherDB>().provideWeatherDAO() }

    single { provideClient() }

    single{ getDataService(get())}

    single<WeatherSearchingRepo> {
        WeatherSearchingRepoImpl(get(), get())
    }
}

fun getDataDI(): List<Module> {
    return listOf(dataModule)
}