package com.ngocpv.domain.usercase

abstract class BaseUseCase<Param, Result> {
    abstract suspend operator fun invoke(param: Param): Result
}

abstract class BaseUseCaseNoParamNoResult {
    abstract suspend operator fun invoke()
}