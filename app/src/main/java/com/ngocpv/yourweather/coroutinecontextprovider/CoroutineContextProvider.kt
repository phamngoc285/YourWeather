package com.ngocpv.yourweather.coroutinecontextprovider

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

open class CoroutineContextProvider constructor() {
    open val Main : CoroutineContext by lazy { Dispatchers.Main }
    open val IO : CoroutineContext by lazy { Dispatchers.IO }
}