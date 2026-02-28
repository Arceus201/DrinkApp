package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.DrinkOrders
import com.example.drinkapp.data.model.RevenueStatistics
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.retrofit.RevenueApiService
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.ErrorHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RevenueRepositoryImpl(
    private val apiService: RevenueApiService
) : RevenueRepository {

    override suspend fun getRevenueStatistics(startTime: String, endTime: String): Result<List<RevenueStatistics>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getRevenueStatisticsCoroutine(startTime, endTime)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.revenue.isNotEmpty()) {
                    Result.Success(body.revenue)
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

    override suspend fun getAllDrinkOrders(name: String, startTime: String, endTime: String): Result<List<DrinkOrders>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getAllDrinkOrdersCoroutine(name, startTime, endTime)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.drinkOrders.isNotEmpty()) {
                    Result.Success(body.drinkOrders)
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
