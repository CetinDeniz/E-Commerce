package com.axuca.app.domain

interface UseCase<in Params, out Result> {
    suspend operator fun invoke(params: Params): Result
}