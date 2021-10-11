package com.ngocpv.domain.di

import com.ngocpv.domain.usercase.RefineLocalDateUseCase
import com.ngocpv.domain.usercase.SearchWeatherUseCase
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.math.sin

val domainModule = module {
    single { SearchWeatherUseCase(get()) }
    single { RefineLocalDateUseCase(get()) }
}

fun getDomainDI() : List<Module>{
    return listOf(domainModule)
}