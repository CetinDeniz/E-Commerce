package com.axuca.app.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axuca.app.data.source.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseViewModel : ViewModel() {
    val baseState = MutableStateFlow<State?>(null)

    fun <T> Flow<Resource<T>>.launchRequest(
        onError: () -> Unit = {},
        onComplete: suspend (T) -> Unit = {}
    ) {
        onEach {
            when (it) {
                is Resource.Loading -> {
                    baseState.emit(State.Loading)
                }

                is Resource.Success -> {
                    baseState.emit(State.Success)
                    onComplete(it.data)
                }

                is Resource.Error -> {
                    baseState.emit(State.Error(it.error))
                    onError()
                }
            }
        }.launchIn(viewModelScope)
    }

}