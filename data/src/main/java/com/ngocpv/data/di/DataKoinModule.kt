package com.ngocpv.data.di

import org.koin.core.module.Module
import org.koin.dsl.module

val dataModule = module {

}

fun getDataDI(): List<Module> {
    return listOf(dataModule)
}