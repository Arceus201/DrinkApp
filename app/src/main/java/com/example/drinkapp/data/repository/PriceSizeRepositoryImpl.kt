package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.PriceSize
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.pricesize.PriceSizeDTO
import com.example.drinkapp.data.resource.retrofit.PriceSizeApiService
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.ErrorHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PriceSizeRepositoryImpl(
    private val apiService: PriceSizeApiService
) : PriceSizeRepository {

    override suspend fun getAllPriceSizeByProductID(id: Long): Result<List<PriceSize>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getAllPriceSizeByProductIDCoroutine(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.pricesizes)
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

    override suspend fun getAllPriceSizeClientByProductID(id: Long): Result<List<PriceSize>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getAllPriceSizeClientByProductIDCoroutine(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.pricesizes)
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

    override suspend fun addPriceSize(idProduct: Long, idSize: Long, price: Double): Result<List<PriceSize>> = withContext(Dispatchers.IO) {
        try {
            val priceSizeDTO = PriceSizeDTO(idProduct, idSize, price, 1L)
            val response = apiService.addPriceSizeCoroutine(priceSizeDTO)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.pricesizes)
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

    override suspend fun updatePriceSizeDefault(idProduct: Long, idSize: Long, price: Double, status: Long): Result<PriceSize> = withContext(Dispatchers.IO) {
        try {
            val priceSizeDTO = PriceSizeDTO(idProduct, idSize, price, status)
            val response = apiService.updatePriceSizeDefaultCoroutine(priceSizeDTO)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.pricesize)
                } else {
                    Result.Error(Exception("Add PriceSize Default không thành công"))
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

    override suspend fun updatePriceSize(id: Long, idProduct: Long, idSize: Long, price: Double, status: Long): Result<List<PriceSize>> = withContext(Dispatchers.IO) {
        try {
            val priceSizeDTO = PriceSizeDTO(idProduct, idSize, price, status)
            val response = apiService.updatePriceSizeCoroutine(id, priceSizeDTO)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.pricesizes)
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
