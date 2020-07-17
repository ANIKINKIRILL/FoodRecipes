package com.anikinkirill.foodrecipes.util

sealed class Resource<T>(
    private val data: T? = null,
    private val message: String? = null
) {

    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(data: T? = null, message: String) : Resource<T>(data, message)

}