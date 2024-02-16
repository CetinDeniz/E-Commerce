package com.axuca.app.data.repository

import com.axuca.app.data.model.Product
import com.axuca.app.data.source.network.NetworkService
import com.axuca.app.data.source.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val service: NetworkService
) {

    fun getProducts(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Success(service.getProducts()))
    }.handleResponse()


    private fun <V, T : Response<V>> Flow<Resource<T>>.handleResponse(): Flow<Resource<V>> =
        onStart { emit(Resource.Loading) }
            .map {
                when (it) {
                    is Resource.Success -> {
                        with(it.data) {
                            if (isSuccessful && body() != null) {
                                Resource.Success(body()!!)
                            } else {
                                val errorBody = errorBody()?.charStream() ?: "NULL ERROR BODY"
                                val errorMessage = "ERROR CODE -> ${code()} \n" + "ERROR BODY -> $errorBody"
                                Resource.Error(IOException(errorMessage))
                            }
                        }
                    }
                    is Resource.Loading -> Resource.Loading
                    else -> it as Resource<V>
                }
            }
            .catch { cause: Throwable ->
                cause.printStackTrace()
                emit(Resource.Error(cause))
            }
            .flowOn(Dispatchers.IO) // This affects only the upper flows
}