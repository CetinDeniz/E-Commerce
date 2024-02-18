package com.axuca.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.axuca.app.base.BaseViewModel
import com.axuca.app.data.model.Product
import com.axuca.app.data.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

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