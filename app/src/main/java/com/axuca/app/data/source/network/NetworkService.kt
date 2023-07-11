package com.axuca.app.data.source.network

import com.axuca.app.data.model.Product
import retrofit2.Response
import retrofit2.http.GET

interface NetworkService {

    @GET("products")
    suspend fun getProducts(): Response<List<Product>>
}