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

// Extension Functions for Result

/**
 * Transform success data while preserving errors
 */
inline fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> = when (this) {
    is Result.Success -> Result.Success(transform(data))
    is Result.Error -> this
    is Result.HttpError -> this
}

/**
 * Chain Result-returning operations
 */
inline fun <T, R> Result<T>.flatMap(transform: (T) -> Result<R>): Result<R> = when (this) {
    is Result.Success -> transform(data)
    is Result.Error -> this
    is Result.HttpError -> this
}

/**
 * Execute side-effect on success only
 */
inline fun <T> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    if (this is Result.Success) action(data)
    return this
}

/**
 * Execute side-effect on failure only
 */
inline fun <T> Result<T>.onFailure(action: (String) -> Unit): Result<T> {
    when (this) {
        is Result.Error -> action(message)
        is Result.HttpError -> action(message)
        is Result.Success -> {}
    }
    return this
}

/**
 * Get data or null
 */
fun <T> Result<T>.getOrNull(): T? = when (this) {
    is Result.Success -> data
    else -> null
}

/**
 * Get data or default value
 */
fun <T> Result<T>.getOrElse(default: T): T = when (this) {
    is Result.Success -> data
    else -> default
}

/**
 * Exhaustive handling of all Result variants
 */
inline fun <T, R> Result<T>.fold(
    onSuccess: (T) -> R,
    onError: (Exception, String) -> R,
    onHttpError: (Int, String, String?) -> R
): R = when (this) {
    is Result.Success -> onSuccess(data)
    is Result.Error -> onError(exception, message)
    is Result.HttpError -> onHttpError(code, message, errorBody)
}
