package com.sky.domain.common

sealed class Result<out R> {

    class Success<T>(val data: T) : Result<T>()
    class Error<T>(val exception: String) : Result<T>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}
