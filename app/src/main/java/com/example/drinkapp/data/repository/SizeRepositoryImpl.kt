package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.Size
import com.example.drinkapp.data.repository.base.BaseRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.size.SizeDTO
import com.example.drinkapp.data.resource.map
import com.example.drinkapp.data.resource.retrofit.SizeApiService
import javax.inject.Inject

class SizeRepositoryImpl @Inject constructor(
    private val apiService: SizeApiService
) : BaseRepository(), SizeRepository {

    override suspend fun getAllSizes(): Result<List<Size>> =
        safeApiCall { apiService.getAllCoroutine() }
            .map { it.sizes }

    override suspend fun addSize(name: String): Result<List<Size>> =
        safeApiCall { apiService.addSizeCoroutine(SizeDTO(name)) }
            .map { it.sizes }

    override suspend fun updateSize(id: Long?, size: Size): Result<List<Size>> =
        safeApiCall { apiService.updateSizeCoroutine(id, size) }
            .map { it.sizes }
}
