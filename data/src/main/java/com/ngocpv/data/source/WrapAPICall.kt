package com.ngocpv.data.source

import com.ngocpv.data.model.BaseResponse
import com.ngocpv.domain.repository.ResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

suspend inline fun <reified T> wrapAPICall(crossinline apiToCall: suspend () -> BaseResponse) : ResponseHandler<T>{
    return try {
        val response = withContext(Dispatchers.IO){
            apiToCall()
        }
        when(response.error){
            1 -> ResponseHandler.Loading
            else -> ResponseHandler.Failure()
        }
    } catch (ex : Exception){
        ex.printStackTrace()
        ResponseHandler.Failure()
    }
}