package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.PriceSize
import com.example.drinkapp.data.repository.base.BaseRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.pricesize.PriceSizeDTO
import com.example.drinkapp.data.resource.map
import com.example.drinkapp.data.resource.retrofit.PriceSizeApiService
import javax.inject.Inject

class PriceSizeRepositoryImpl @Inject constructor(
    private val apiService: PriceSizeApiService
) : BaseRepository(), PriceSizeRepository {

    override suspend fun getAllPriceSizeByProductID(id: Long): Result<List<PriceSize>> =
        safeApiCall { apiService.getAllPriceSizeByProductIDCoroutine(id) }
            .map { it.pricesizes }

    override suspend fun getAllPriceSizeClientByProductID(id: Long): Result<List<PriceSize>> =
        safeApiCall { apiService.getAllPriceSizeClientByProductIDCoroutine(id) }
            .map { it.pricesizes }

    override suspend fun addPriceSize(idProduct: Long, idSize: Long, price: Double): Result<List<PriceSize>> {
        val priceSizeDTO = PriceSizeDTO(idProduct, idSize, price, 1L)
        return safeApiCall { apiService.addPriceSizeCoroutine(priceSizeDTO) }
            .map { it.pricesizes }
    }

    override suspend fun updatePriceSizeDefault(idProduct: Long, idSize: Long, price: Double, status: Long): Result<PriceSize> {
        val priceSizeDTO = PriceSizeDTO(idProduct, idSize, price, status)
        return safeApiCall { apiService.updatePriceSizeDefaultCoroutine(priceSizeDTO) }
            .map { it.pricesize }
    }

    override suspend fun updatePriceSize(id: Long, idProduct: Long, idSize: Long, price: Double, status: Long): Result<List<PriceSize>> {
        val priceSizeDTO = PriceSizeDTO(idProduct, idSize, price, status)
        return safeApiCall { apiService.updatePriceSizeCoroutine(id, priceSizeDTO) }
            .map { it.pricesizes }
    }
}
