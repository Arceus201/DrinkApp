package com.example.drinkapp.data.resource.call

import com.example.drinkapp.data.model.Category
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.dto.category.CategoryDTO
import com.example.drinkapp.data.resource.response.category.CategoryResponse
import com.example.drinkapp.data.resource.retrofit.CategoryApiService
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.ErrorHandler
import com.example.drinkapp.utils.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallApiCategory {
    private val categoryApiService: CategoryApiService by lazy {
        RetrofitHelper.getInstance().create(CategoryApiService::class.java)
    }
    fun getAllCategory(listen: OnResultListener<List<Category>>) {
        val call = categoryApiService.getall()
        callApi(call,listen)
    }

    fun addCategory(name: String,listen: OnResultListener<List<Category>>) {
        val categoryDTO = CategoryDTO(name)
        val call = categoryApiService.addCategory(categoryDTO)
        callApi(call,listen)
    }

    fun updateCategory(id: Long?, category: Category,listen: OnResultListener<List<Category>>) {
        val call = categoryApiService.updateCategory(id, category)
        callApi(call,listen)
    }

    private fun callApi(call: Call<CategoryResponse>, listen: OnResultListener<List<Category>>) {
        call.enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                if (response.isSuccessful) {
                    val categoriesResponse = response.body()
                    if (categoriesResponse != null) {
                        val categories = categoriesResponse.categories
                        if (categories != null && categories.isNotEmpty()) {
                            listen.onSuccess(categories)
                        } else {
                            listen.onFail(Constant.MESS_CALL_API_NOT_FOUND)
                        }
                    } else {
                        listen.onFail(Constant.MESS_CALL_API_NOT_FOUND)
                    }
                } else {
                    val errorMessage = ErrorHandler.getErrorMessage(response)
                    listen.onFail(errorMessage)
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                val errorMessage = ErrorHandler.getNetworkErrorMessage(t)
                listen.onFail(errorMessage)
            }
        })
    }
    companion object{
        private var instance: CallApiCategory? = null
        fun getInstance() = synchronized(this){
            instance?: CallApiCategory().also { instance = it }
        }
    }
}