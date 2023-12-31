package com.axuca.app.data.source.network

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val error: Throwable) : Resource<T>()
}
