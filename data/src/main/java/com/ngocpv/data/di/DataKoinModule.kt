package com.ngocpv.data.di

import com.ngocpv.data.repository.WeatherSearchingRepoImpl
import com.ngocpv.data.source.getDataService
import com.ngocpv.data.source.provideClient
import com.ngocpv.domain.repository.WeatherSearchingRepo
import org.koin.core.module.Module
import org.koin.dsl.module

val dataModule = module {
    single { provideClient() }

    single{ getDataService(get())}

    single<WeatherSearchingRepo> {
        WeatherSearchingRepoImpl(get())
    }
}

fun getDataDI(): List<Module> {
    return listOf(dataModule)
}