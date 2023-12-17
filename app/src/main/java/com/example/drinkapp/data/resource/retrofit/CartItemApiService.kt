package com.example.drinkapp.data.resource.retrofit

import com.example.drinkapp.data.resource.dto.cartitem.CartItemDTO
import com.example.drinkapp.data.resource.dto.cartitem.CartItemUpateDTO
import com.example.drinkapp.data.resource.response.cartitem.CartItemListReponse
import com.example.drinkapp.data.resource.response.cartitem.CartItemReponse
import com.example.drinkapp.utils.ApiConstant
import retrofit2.Call
import retrofit2.http.*

interface CartItemApiService {
    @POST(ApiConstant.API_KEY_ADD_CARTITEM)
    fun addCartItem(@Body cartItemDTO: CartItemDTO): Call<CartItemReponse>

    @PUT(ApiConstant.API_KEY_UPDTAE_CARTITEM_NUMBER)
    fun updateCartItemNumber(@Path("id") id: Long, @Path("number") number: Long): Call<CartItemReponse>
    @PUT(ApiConstant.API_KEY_UPDATE_CARTITEM)
    fun updateCartItem(@Path("id") id: Long, @Body cartItemUpdateDTO: CartItemUpateDTO): Call<CartItemReponse>

    @GET(ApiConstant.API_KEY_GET_ALL_CARTITEM_BY_USER_ID)
    fun getAllCartItemByUserID(@Path("id") id: Long): Call<CartItemListReponse>

    @GET(ApiConstant.API_KEY_CHECK_CARTITEM)
    fun checkCartItem(@Path("user_id") user_id: Long, @Path("pricesize_id") pricesize_id: Long): Call<CartItemReponse>


    @DELETE(ApiConstant.API_KEY_DELETE_CARTITEM_BY_ID)
    fun deleteCartItemById(@Path("id") id: Long): Call<CartItemReponse>

    @DELETE(ApiConstant.API_KEY_DELETE_ALL_CARTITEM)
    fun deleteAll(@Path("user_id") user_id: Long): Call<CartItemReponse>

}