package com.anikinkirill.foodrecipes.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val status: Status? = null
) {

    class Success<T>(data: T) : Resource<T>(data, status = Status.SUCCESS)
    class Loading<T>(data: T? = null) : Resource<T>(data, status = Status.LOADING)
    class Error<T>(data: T? = null, message: String) : Resource<T>(data, message, status = Status.ERROR)

    sealed class Status {
        object SUCCESS : Status()
        object LOADING : Status()
        object ERROR : Status()
    }

}