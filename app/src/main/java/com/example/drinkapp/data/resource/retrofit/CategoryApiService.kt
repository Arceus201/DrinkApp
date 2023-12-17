package com.example.drinkapp.data.resource.retrofit


import com.example.drinkapp.data.model.Category
import com.example.drinkapp.data.resource.dto.category.CategoryDTO
import com.example.drinkapp.data.resource.response.category.CategoryResponse
import com.example.drinkapp.utils.ApiConstant
import retrofit2.Call
import retrofit2.http.*


interface CategoryApiService {

    @GET(ApiConstant.API_KEY_CATEGORY_GET_ALL)
    fun getall(): Call<CategoryResponse>

    @POST(ApiConstant.API_KEY_CATEGORY_ADD)
    fun addCategory(@Body categoryDTO: CategoryDTO) : Call<CategoryResponse>

    @PUT(ApiConstant.API_KE_CATEGORY_UPDATE)
    fun updateCategory(@Path("id") id: Long?, @Body category: Category): Call<CategoryResponse>
}
