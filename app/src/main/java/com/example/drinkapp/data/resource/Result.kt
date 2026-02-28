package com.example.drinkapp.data.resource

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    
    data class Error(
        val exception: Exception,
        val message: String = exception.message ?: "Unknown error"
    ) : Result<Nothing>()
    
    data class HttpError(
        val code: Int,
        val message: String,
        val errorBody: String? = null
    ) : Result<Nothing>()
}
