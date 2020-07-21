package com.anikinkirill.foodrecipes.responses

import retrofit2.Response
import java.io.IOException

sealed class ApiResponse<T> {

    fun create(error: Throwable) : ApiResponse<T> {
        return ApiErrorResponse((if (error.message != "") error.message else "Unknown error\\nCheck network connection").toString())
    }

    fun create(response: Response<T>) : ApiResponse<T> {
        if(response.isSuccessful){
            val body: T? = response.body()
            return if(body == null || response.code() == 204){
                ApiEmptyResponse()
            }else{
                ApiSuccessResponse(body)
            }
        }else{
            val errorMessage: String
            errorMessage = try {
                response.errorBody().toString()
            }catch (ex: IOException) {
                ex.printStackTrace()
                response.message()
            }
            return ApiErrorResponse(errorMessage)
        }
    }

    data class ApiSuccessResponse<T>(
        var body: T
    ) : ApiResponse<T>()

    data class ApiErrorResponse<T>(
        private var errorMessage: String
    ) : ApiResponse<T>()

    class ApiEmptyResponse<T> : ApiResponse<T>()

}