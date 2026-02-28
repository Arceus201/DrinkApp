package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.Size
import com.example.drinkapp.data.resource.Result

interface SizeRepository {
    suspend fun getAllSizes(): Result<List<Size>>
    suspend fun addSize(name: String): Result<List<Size>>
    suspend fun updateSize(id: Long?, size: Size): Result<List<Size>>
}
