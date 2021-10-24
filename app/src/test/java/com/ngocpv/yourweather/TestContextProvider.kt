package com.ngocpv.yourweather

import com.ngocpv.yourweather.coroutinecontextprovider.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class TestContextProvider : CoroutineContextProvider() {
    override val IO: CoroutineContext
        get() = Dispatchers.Unconfined

    override val Main: CoroutineContext
        get() = Dispatchers.Unconfined
}