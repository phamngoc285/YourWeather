package com.ngocpv.yourweather.di

import com.ngocpv.data.di.getDataDI
import com.ngocpv.domain.di.getDomainDI
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule = module {

}

fun getDI(): List<Module> {
    return mutableListOf(appModule).also {
        it.addAll(getDataDI())
        it.addAll(getDomainDI())
    }
}