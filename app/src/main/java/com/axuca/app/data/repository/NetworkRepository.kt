package com.axuca.app.data.repository

import com.axuca.app.data.model.Product
import com.axuca.app.data.source.network.NetworkService
import com.axuca.app.data.source.network.Resource
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val service: NetworkService
) {

    suspend fun getProducts(): Resource<List<Product>> {
        return networkCall {
            service.getProducts()
        }
    }


    private suspend fun <T> networkCall(call: suspend () -> T): T {
        return call()
    }
}