package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.DrinkOrders
import com.example.drinkapp.data.model.RevenueStatistics
import com.example.drinkapp.data.repository.base.BaseRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.map
import com.example.drinkapp.data.resource.retrofit.RevenueApiService
import javax.inject.Inject

class RevenueRepositoryImpl @Inject constructor(
    private val apiService: RevenueApiService
) : BaseRepository(), RevenueRepository {

    override suspend fun getRevenueStatistics(startTime: String, endTime: String): Result<List<RevenueStatistics>> =
        safeApiCall { apiService.getRevenueStatisticsCoroutine(startTime, endTime) }
            .map { it.revenue }

    override suspend fun getAllDrinkOrders(name: String, startTime: String, endTime: String): Result<List<DrinkOrders>> =
        safeApiCall { apiService.getAllDrinkOrdersCoroutine(name, startTime, endTime) }
            .map { it.drinkOrders }
}
