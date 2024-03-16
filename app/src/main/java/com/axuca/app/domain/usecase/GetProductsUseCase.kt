package com.axuca.app.domain.usecase

import com.axuca.app.data.repository.NetworkRepository
import com.axuca.app.data.source.remote.Resource
import com.axuca.app.domain.UseCase
import com.axuca.app.domain.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) : UseCase<Unit, Flow<Resource<List<Product>>>> { // Unit indicates no input parameters required

    override suspend fun invoke(params: Unit): Flow<Resource<List<Product>>> =
        networkRepository.getProducts()
}