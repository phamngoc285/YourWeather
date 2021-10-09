package com.ngocpv.domain.di

import com.ngocpv.domain.usercase.SearchWeatherUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

val domainModule = module {
    single { SearchWeatherUseCase(get()) }
}

fun getDomainDI() : List<Module>{
    return listOf(domainModule)
}