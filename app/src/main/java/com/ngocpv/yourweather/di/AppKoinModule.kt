package com.ngocpv.yourweather.di

import com.ngocpv.data.di.getDataDI
import com.ngocpv.domain.di.getDomainDI
import com.ngocpv.yourweather.ui.main.WeatherSearchingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule = module {
    viewModel { WeatherSearchingViewModel(get()) }
}

fun getDI(): List<Module> {
    return mutableListOf(appModule).also {
        it.addAll(getDomainDI())
        it.addAll(getDataDI())
    }
}