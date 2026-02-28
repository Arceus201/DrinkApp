package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.Category
import com.example.drinkapp.data.resource.Result

interface CategoryRepository {
    suspend fun getAllCategories(): Result<List<Category>>
    suspend fun addCategory(name: String): Result<List<Category>>
    suspend fun updateCategory(id: Long?, category: Category): Result<List<Category>>
}
