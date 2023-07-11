package com.axuca.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.axuca.app.base.BaseViewModel
import com.axuca.app.data.model.Product
import com.axuca.app.data.repository.NetworkRepository
import com.axuca.app.data.source.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
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
        viewModelScope.launch {
            Timber.d("ViewModel init")
            networkRepository.getProducts().collect {
                state.emit(
                    when (it) {
                        is Resource.Loading -> {
                            Timber.d("Loading")
                            HomeState.Loading
                        }
                        is Resource.Success -> {
                            Timber.d(it.data.size.toString())
                            HomeState.AllProducts(data = it.data)
                        }
                        is Resource.Error -> {
                            Timber.d("Error : ${it.error.stackTrace}")
                            HomeState.Error(throwable = it.error)
                        }
                    }
                )
            }
        }
    }

    sealed class HomeState {
        class AllProducts(val data: List<Product>) : HomeState()
        object Loading : HomeState()
        class Error(val throwable: Throwable) : HomeState()
    }
}