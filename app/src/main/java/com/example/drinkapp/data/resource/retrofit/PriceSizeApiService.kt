package com.example.drinkapp.data.resource.retrofit


import com.example.drinkapp.data.resource.dto.pricesize.PriceSizeDTO
import com.example.drinkapp.data.resource.response.pricesize.PriceSizeListReponse
import com.example.drinkapp.data.resource.response.pricesize.PriceSizeReponse
import com.example.drinkapp.utils.ApiConstant
import retrofit2.Call
import retrofit2.http.*

interface PriceSizeApiService {
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
}