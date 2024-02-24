package com.axuca.app.ui.home

import com.axuca.app.base.viewmodel.BaseViewModel
import com.axuca.app.data.model.Product
import com.axuca.app.data.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : BaseViewModel() {

    val state = MutableStateFlow<HomeState?>(null)

    init {
        networkRepository.getProducts().launchRequest {
            state.emit(HomeState.AllProducts(it))
        }
    }

    sealed class HomeState {
        class AllProducts(val data: List<Product>) : HomeState()
    }
}