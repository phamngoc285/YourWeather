package com.ngocpv.data.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.ngocpv.data.repository.WeatherSearchingRepoImpl
import com.ngocpv.data.source.database.YourWeatherDB
import com.ngocpv.data.source.getDataService
import com.ngocpv.data.source.provideClient
import com.ngocpv.domain.repository.WeatherSearchingRepo
import org.koin.core.module.Module
import org.koin.dsl.module

val dataModule = module {
    single {
            Log.d("ngocpv1", "context " + get<Context>())
            val db = Room.databaseBuilder(get(), YourWeatherDB::class.java, "Your-Weather-DB")
                .build()
            Log.d("ngocpv1", "db $db")
            db
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