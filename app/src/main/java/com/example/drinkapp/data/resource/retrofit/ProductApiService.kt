package com.example.drinkapp.data.resource.retrofit

import com.example.drinkapp.data.resource.dto.product.ProductDTO
import com.example.drinkapp.data.resource.response.BaseResponse
import com.example.drinkapp.data.resource.response.product.ProductResponse
import com.example.drinkapp.data.resource.response.product.ProductListResponse
import com.example.drinkapp.utils.ApiConstant
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ProductApiService {
    @POST(ApiConstant.API_KEY_PRODUCT_ADD)
    fun addProduct(@Body productDTO: ProductDTO): Call<ProductResponse>

    @PUT(ApiConstant.API_KEY_PRODUCT_UPDATE)
    fun updateProduct(@Path("id") id: Long?, @Body productDTO: ProductDTO) :Call<ProductResponse>

    @GET(ApiConstant.API_KEY_PRODUCT_GET_ALL)
    fun getALlProduct(): Call<ProductListResponse>

    @GET(ApiConstant.API_KEY_PRODUCT_GET_HOT)
    fun getProductHot(): Call<ProductListResponse>

    @GET(ApiConstant.API_KEY_PRODUCT_GET_ALL_CLIENT)
    fun getAllProductClient(): Call<ProductListResponse>

    @GET(ApiConstant.API_KEY_PRODUCT_GET_BY_ID)
    fun getProductById(@Path("id") id: Long) :Call<ProductResponse>

    @DELETE(ApiConstant.API_KEY_PRODUCT_DELETE)
    fun deleteProductById(@Path("id") id: Long) :Call<BaseResponse>

}