package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.Size
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.size.SizeDTO
import com.example.drinkapp.data.resource.retrofit.SizeApiService
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.ErrorHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SizeRepositoryImpl(
    private val apiService: SizeApiService
) : SizeRepository {

    override suspend fun getAllSizes(): Result<List<Size>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getAllCoroutine()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.sizes.isNotEmpty()) {
                    Result.Success(body.sizes)
                } else {
                    Result.Error(Exception(Constant.MESS_CALL_API_NOT_FOUND))
                }
            } else {
                val errorMessage = ErrorHandler.getErrorMessage(response)
                Result.HttpError(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            val errorMessage = ErrorHandler.getNetworkErrorMessage(e)
            Result.Error(e, errorMessage)
        }
    }

    override suspend fun addSize(name: String): Result<List<Size>> = withContext(Dispatchers.IO) {
        try {
            val sizeDTO = SizeDTO(name)
            val response = apiService.addSizeCoroutine(sizeDTO)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.sizes.isNotEmpty()) {
                    Result.Success(body.sizes)
                } else {
                    Result.Error(Exception(Constant.MESS_CALL_API_NOT_FOUND))
                }
            } else {
                val errorMessage = ErrorHandler.getErrorMessage(response)
                Result.HttpError(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            val errorMessage = ErrorHandler.getNetworkErrorMessage(e)
            Result.Error(e, errorMessage)
        }
    }

    override suspend fun updateSize(id: Long?, size: Size): Result<List<Size>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.updateSizeCoroutine(id, size)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.sizes.isNotEmpty()) {
                    Result.Success(body.sizes)
                } else {
                    Result.Error(Exception(Constant.MESS_CALL_API_NOT_FOUND))
                }
            } else {
                val errorMessage = ErrorHandler.getErrorMessage(response)
                Result.HttpError(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            val errorMessage = ErrorHandler.getNetworkErrorMessage(e)
            Result.Error(e, errorMessage)
        }
    }
}
