package com.ngocpv.domain.di

import org.koin.core.module.Module
import org.koin.dsl.module

val domainModule = module {

}

fun getDomainDI() : List<Module>{
    return listOf(domainModule)
}