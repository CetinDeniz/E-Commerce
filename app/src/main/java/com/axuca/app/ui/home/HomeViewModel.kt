package com.axuca.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axuca.app.data.model.Product
import com.axuca.app.data.source.network.NetworkService
import com.axuca.app.data.source.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val service: NetworkService
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private lateinit var response: List<Product>

    val state = MutableStateFlow<HomeState?>(null)

    init {
        viewModelScope.launch {
            val deferredResponse = viewModelScope.async {
                service.getProducts()
            }

            val response = deferredResponse.await() as Resource.Success
            val _state = HomeState.AllProducts(data = response.data)
            state.emit(_state)

            Timber.d(_state.data.size.toString())
        }
    }


    sealed class HomeState {
        class AllProducts(val data: List<Product>) : HomeState()
    }
}