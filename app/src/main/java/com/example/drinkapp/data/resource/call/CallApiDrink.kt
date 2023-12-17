package com.example.drinkapp.data.resource.call

import com.example.drinkapp.data.model.Product
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.dto.product.ProductDTO
import com.example.drinkapp.data.resource.response.BaseResponse
import com.example.drinkapp.data.resource.response.product.ProductResponse
import com.example.drinkapp.data.resource.response.product.ProductListResponse
import com.example.drinkapp.data.resource.retrofit.ProductApiService
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallApiDrink{
    private val productApiService: ProductApiService by lazy {
        RetrofitHelper.getInstance().create(ProductApiService::class.java)
    }
    fun getAllDrink(listen: OnResultListener<List<Product>>) {
        val call = productApiService.getALlProduct()
        callApiList(call,listen)
    }

    fun getDrinkHot(listen: OnResultListener<List<Product>>) {
        val call = productApiService.getProductHot()
        callApiList(call,listen)
    }

    fun getAllDrinkClient(listen: OnResultListener<List<Product>>) {
        val call = productApiService.getAllProductClient()
        callApiList(call,listen)
    }

    fun addProduct(name: String,imageUri: String, price:Double, statusCode: Long,cateId: Long,listen: OnResultListener<Product>) {
        val productDTO = ProductDTO(name, imageUri,price, statusCode, cateId)
        val call = productApiService.addProduct(productDTO)
        callApi(call,listen)
    }

    fun updateProduct(id: Long,name: String,imageUri: String, price: Double, statusCode: Long,cateId: Long,listen: OnResultListener<Product>){
        val productDTO = ProductDTO(name, imageUri,price, statusCode, cateId)
        val call = productApiService.updateProduct(id,productDTO)
        callApi(call,listen)
    }
    fun getProductById(id: Long,listen: OnResultListener<Product>){
        val call = productApiService.getProductById(id)
        callApi(call,listen)
    }

    fun deleteProductById(id: Long,listen: OnResultListener<String>){
        val call = productApiService.deleteProductById(id)
        callApiDelete(call,listen)
    }


    private fun callApiList(call: Call<ProductListResponse>, listen: OnResultListener<List<Product>>) {
        call.enqueue(object : Callback<ProductListResponse> {
            override fun onResponse(
                call: Call<ProductListResponse>,
                response: Response<ProductListResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        listen.onSuccess(result.products)
                    } else {
                        listen.onFail(Constant.MESS_CALL_API_NOT_FOUND)
                    }
                } else {
                    listen.onFail(Constant.MESS_CALL_API_FAIL)
                }
            }

            override fun onFailure(call: Call<ProductListResponse>, t: Throwable) {
                val errorResponse = t.message ?: "Network error"
                listen.onFail(errorResponse)
            }
        })
    }


    private fun callApi(call: Call<ProductResponse>, listen: OnResultListener<Product>) {
        call.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        listen.onSuccess(result.product)
                    } else {
                        listen.onFail(Constant.MESS_CALL_API_NOT_FOUND)
                    }
                } else {
                    listen.onFail(Constant.MESS_CALL_API_FAIL)
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                val errorResponse = t.message ?: "Network error"
                listen.onFail(errorResponse)
            }
        })
    }

    private fun callApiDelete(call: Call<BaseResponse>, listen: OnResultListener<String>) {
        call.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(
                call: Call<BaseResponse>,
                response: Response<BaseResponse>
            ) {
                if (response.isSuccessful) {
                    listen.onSuccess(Constant.MESS_CALL_API_SUCCESS)
                } else {
                    listen.onFail(Constant.MESS_CALL_API_FAIL)
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                val errorResponse = t.message ?: "Network error"
                listen.onFail(errorResponse)
            }
        })
    }
    companion object{
        private var instance: CallApiDrink? = null
        fun getInstance() = synchronized(this){
            instance?: CallApiDrink().also { instance = it }
        }
    }
}