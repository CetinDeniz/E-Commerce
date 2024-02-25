package com.axuca.app.data.source.remote

import com.axuca.app.domain.model.Product
import retrofit2.Response
import retrofit2.http.GET

interface NetworkService {

    @GET("products")
    suspend fun getProducts(): Response<List<Product>>
}