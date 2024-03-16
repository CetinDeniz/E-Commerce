package com.axuca.app.presentation.productDetail

import android.util.Log
import com.axuca.app.base.viewmodel.BaseViewModel
import com.axuca.app.data.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : BaseViewModel() {

    fun getProduct(id: Int) {
        networkRepository.getProduct(id).launchRequest(
            onComplete = {
                Timber.d("Get Product : complete", it.toString())
            },
            onError = {
                Timber.d("Get Product : error", it.toString())
            }
        )
    }

}