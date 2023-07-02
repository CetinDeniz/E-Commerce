package com.axuca.app.data.source.network

sealed class Resource<T> {
    object Loading : Resource<Nothing>()
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val error: T) : Resource<T>()
}
