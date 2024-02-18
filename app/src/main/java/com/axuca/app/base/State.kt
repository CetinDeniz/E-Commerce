package com.axuca.app.base

sealed class State {
    data object Loading: State()
    data object Success: State()
    class Error(val throwable: Throwable): State()
}