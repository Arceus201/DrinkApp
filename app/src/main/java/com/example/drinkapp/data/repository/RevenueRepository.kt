package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.DrinkOrders
import com.example.drinkapp.data.model.RevenueStatistics
import com.example.drinkapp.data.resource.Result

interface RevenueRepository {
    suspend fun getRevenueStatistics(startTime: String, endTime: String): Result<List<RevenueStatistics>>
    suspend fun getAllDrinkOrders(name: String, startTime: String, endTime: String): Result<List<DrinkOrders>>
}
