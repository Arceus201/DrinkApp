package com.example.drinkapp.data.resource.retrofit


import com.example.drinkapp.data.resource.dto.pricesize.PriceSizeDTO
import com.example.drinkapp.data.resource.response.pricesize.PriceSizeListReponse
import com.example.drinkapp.data.resource.response.pricesize.PriceSizeReponse
import com.example.drinkapp.utils.ApiConstant
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface PriceSizeApiService {
    // Old callback methods (keep for backward compatibility)
    @POST(ApiConstant.API_KEY_PRICESIZE_ADD)
    fun addPriceSize(@Body priceSizeDTO: PriceSizeDTO): Call<PriceSizeListReponse>

    @PUT(ApiConstant.API_KEY_PRICESIZE_UPDTAE)
    fun updatePriceSize(@Path("id") id: Long?,@Body priceSizeDTO: PriceSizeDTO): Call<PriceSizeListReponse>

    @PUT(ApiConstant.API_KEY_PRICESIZE_UPDTAE_DEFAULT)
    fun updatePriceSizeDefault(@Body priceSizeDTO: PriceSizeDTO): Call<PriceSizeReponse>

    @GET(ApiConstant.API_KEY_PRICESIZE_GET_ALL_BY_PRODUCT_ID)
    fun getAllPriceSizeByProductID(@Path("id") id: Long?): Call<PriceSizeListReponse>

    @GET(ApiConstant.API_KEY_PRICESIZE_GET_ALL_CLIENT_BY_PRODUCT_ID)
    fun getAllPriceSizeClientByProductID(@Path("id") id: Long?): Call<PriceSizeListReponse>

    // New coroutine methods
    @POST(ApiConstant.API_KEY_PRICESIZE_ADD)
    suspend fun addPriceSizeCoroutine(@Body priceSizeDTO: PriceSizeDTO): Response<PriceSizeListReponse>

    @PUT(ApiConstant.API_KEY_PRICESIZE_UPDTAE)
    suspend fun updatePriceSizeCoroutine(@Path("id") id: Long?, @Body priceSizeDTO: PriceSizeDTO): Response<PriceSizeListReponse>

    @PUT(ApiConstant.API_KEY_PRICESIZE_UPDTAE_DEFAULT)
    suspend fun updatePriceSizeDefaultCoroutine(@Body priceSizeDTO: PriceSizeDTO): Response<PriceSizeReponse>

    @GET(ApiConstant.API_KEY_PRICESIZE_GET_ALL_BY_PRODUCT_ID)
    suspend fun getAllPriceSizeByProductIDCoroutine(@Path("id") id: Long?): Response<PriceSizeListReponse>

    @GET(ApiConstant.API_KEY_PRICESIZE_GET_ALL_CLIENT_BY_PRODUCT_ID)
    suspend fun getAllPriceSizeClientByProductIDCoroutine(@Path("id") id: Long?): Response<PriceSizeListReponse>
}