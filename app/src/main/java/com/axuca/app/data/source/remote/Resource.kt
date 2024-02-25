package com.axuca.app.data.source.remote

sealed class Resource<out T> {
    data object Loading : Resource<Nothing>()
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val error: Throwable) : Resource<T>()
}
