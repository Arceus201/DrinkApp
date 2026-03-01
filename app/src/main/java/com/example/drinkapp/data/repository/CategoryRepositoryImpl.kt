package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.Category
import com.example.drinkapp.data.repository.base.BaseRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.category.CategoryDTO
import com.example.drinkapp.data.resource.map
import com.example.drinkapp.data.resource.retrofit.CategoryApiService
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val apiService: CategoryApiService
) : BaseRepository(), CategoryRepository {

    override suspend fun getAllCategories(): Result<List<Category>> =
        safeApiCall { apiService.getAllCoroutine() }
            .map { it.categories }

    override suspend fun addCategory(name: String): Result<List<Category>> =
        safeApiCall { apiService.addCategoryCoroutine(CategoryDTO(name)) }
            .map { it.categories }

    override suspend fun updateCategory(id: Long?, category: Category): Result<List<Category>> =
        safeApiCall { apiService.updateCategoryCoroutine(id, category) }
            .map { it.categories }
}
