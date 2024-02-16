package com.axuca.app.base

import kotlin.Exception

sealed class State {
    data object Loading: State()
    data object Success: State()
    class Error(exception: Throwable): State()
}