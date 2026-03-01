package com.example.drinkapp.data.repository

import com.example.drinkapp.data.repository.base.BaseRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.retrofit.ExChangeRateApiService
import javax.inject.Inject
import javax.inject.Singleton

interface ExchangeRateRepository {
    suspend fun getExchangeRate(): Result<Double>
}

@Singleton
class ExchangeRateRepositoryImpl @Inject constructor(
    private val apiService: ExChangeRateApiService
) : BaseRepository(), ExchangeRateRepository {
    
    override suspend fun getExchangeRate(): Result<Double> {
        val result = safeApiCall { apiService.getExchangeRates() }
        return when (result) {
            is Result.Success -> {
                val vndRate = result.data.conversion_rates["VND"]
                if (vndRate != null) {
                    Result.Success(vndRate)
                } else {
                    Result.Error(Exception("VND rate not found"), "VND rate not found")
                }
            }
            is Result.Error -> result
            is Result.HttpError -> result
        }
    }
}
