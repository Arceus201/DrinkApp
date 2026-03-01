package com.example.drinkapp.data.repository.base

import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.utils.ErrorHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.coroutines.cancellation.CancellationException

/**
 * Abstract base repository providing common API call handling logic.
 * 
 * This class wraps Retrofit API calls with consistent error handling,
 * converting HTTP responses and exceptions into Result sealed class instances.
 */
abstract class BaseRepository {
    
    /**
     * Safely executes a Retrofit API call with comprehensive error handling.
     * 
     * @param apiCall Suspend function that performs the Retrofit API call
     * @return Result.Success with data if successful, Result.HttpError for HTTP errors,
     *         or Result.Error for network/other exceptions
     */
    protected suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): Result<T> = withContext(Dispatchers.IO) {
        try {
            val response = apiCall()
            
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body)
                } else {
                    Result.Error(
                        exception = Exception("Response body is null"),
                        message = "Không có dữ liệu trả về"
                    )
                }
            } else {
                val errorMessage = ErrorHandler.getErrorMessage(response)
                Result.HttpError(
                    code = response.code(),
                    message = errorMessage,
                    errorBody = response.errorBody()?.string()
                )
            }
        } catch (e: CancellationException) {
            // Propagate cancellation without catching for proper coroutine cancellation
            throw e
        } catch (e: Exception) {
            val errorMessage = ErrorHandler.getNetworkErrorMessage(e)
            Result.Error(
                exception = e,
                message = errorMessage
            )
        }
    }
}
