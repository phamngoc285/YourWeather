package com.ngocpv.domain.repository

sealed class ResponseHandler<out T>() {

    data class Success<out T>(val data: T) : ResponseHandler<T>()

    data class Failure(
        val error: String = ""
    ) : ResponseHandler<Nothing>()

    object Loading : ResponseHandler<Nothing>()
}