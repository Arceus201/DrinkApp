package com.example.drinkapp.data.resource.retrofit

import com.example.drinkapp.data.resource.response.drinkorders.DrinkOrdersReponse
import com.example.drinkapp.data.resource.response.revenue.RevenueStatisticsReponse
import com.example.drinkapp.utils.ApiConstant
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RevenueApiService {
    @GET(ApiConstant.API_KEY_REVENUE_GET)
    fun getRevenueStatistics(
        @Query("startTime") startTime: String,
        @Query("endTime") endTime: String
    ): Call<RevenueStatisticsReponse>

    @GET(ApiConstant.API_KEY_REVENUE_DRINK_ORDERS)
    fun getAllDrinkOrders(
        @Query("name") name: String,
        @Query("startTime") startTime: String,
        @Query("endTime") endTime: String
    ): Call<DrinkOrdersReponse>

    // Coroutine versions
    @GET(ApiConstant.API_KEY_REVENUE_GET)
    suspend fun getRevenueStatisticsCoroutine(
        @Query("startTime") startTime: String,
        @Query("endTime") endTime: String
    ): Response<RevenueStatisticsReponse>

    @GET(ApiConstant.API_KEY_REVENUE_DRINK_ORDERS)
    suspend fun getAllDrinkOrdersCoroutine(
        @Query("name") name: String,
        @Query("startTime") startTime: String,
        @Query("endTime") endTime: String
    ): Response<DrinkOrdersReponse>
}