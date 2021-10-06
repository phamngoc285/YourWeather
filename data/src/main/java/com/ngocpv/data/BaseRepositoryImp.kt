package com.ngocpv.data

import com.ngocpv.data.model.BaseResponse
import com.ngocpv.data.source.wrapAPICall
import com.ngocpv.domain.repository.ResponseHandler

abstract class BaseRepositoryImp {
    protected suspend inline fun <reified T> callAPI(crossinline apiToCall: ()  -> BaseResponse) : ResponseHandler<T>{
        return wrapAPICall {
            apiToCall()
        }
    }
}