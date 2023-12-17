package com.example.drinkapp.data.resource.call

import com.example.drinkapp.data.model.CartItem
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.dto.cartitem.CartItemDTO
import com.example.drinkapp.data.resource.dto.cartitem.CartItemUpateDTO
import com.example.drinkapp.data.resource.response.cartitem.CartItemListReponse
import com.example.drinkapp.data.resource.response.cartitem.CartItemReponse
import com.example.drinkapp.data.resource.retrofit.CartItemApiService
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallApiCartItem {
    private val cartItemApiService: CartItemApiService by lazy {
        RetrofitHelper.getInstance().create(CartItemApiService::class.java)
    }

    fun addCartItem(
        pricesize_id: Long,
        user_id: Long,
        number: Long,
        note: String,
        listen: OnResultListener<CartItem>
    ) {
        val cartItemDTO = CartItemDTO(pricesize_id, user_id,  number, note)
        val call = cartItemApiService.addCartItem(cartItemDTO)
        callApi(call, listen)
    }

    fun getAllCartItemByUserID(id: Long, listen: OnResultListener<List<CartItem>>){
        val call = cartItemApiService.getAllCartItemByUserID(id)
        callApiList(call,listen)
    }
    fun updateCartItemNumber(id: Long, number: Long, listen: OnResultListener<CartItem>){
        val call = cartItemApiService.updateCartItemNumber(id,number)
        callApi(call,listen)
    }

    fun updateCartItem(id: Long, number: Long,note: String,listen: OnResultListener<CartItem>){
        val cartItemUpdateDTO = CartItemUpateDTO(number,note)
        val call = cartItemApiService.updateCartItem(id,cartItemUpdateDTO)
        callApi(call,listen)
    }
    fun checkCartItem(user_id: Long, pricesize_id: Long, listen: OnResultListener<CartItem>){
        val call = cartItemApiService.checkCartItem(user_id,pricesize_id)
        callApi(call,listen)
    }

    fun deleteCartItemById(id: Long, listen: OnResultListener<String>){
        val call = cartItemApiService.deleteCartItemById(id)
        callApiDelete(call,listen)
    }

    fun deleteAll(user_id: Long,listen: OnResultListener<String>){
        val call = cartItemApiService.deleteAll(user_id)
        callApiDelete(call, listen)
    }

    private fun callApi(call: Call<CartItemReponse>, listen: OnResultListener<CartItem>) {
        call.enqueue(object : Callback<CartItemReponse> {
            override fun onResponse(
                call: Call<CartItemReponse>,
                response: Response<CartItemReponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        listen.onSuccess(result.cartitem)
                    } else {
                        listen.onFail(Constant.MESS_CALL_API_NOT_FOUND)
                    }
                } else {
                    listen.onFail(Constant.MESS_CALL_API_FAIL)
                }
            }

            override fun onFailure(call: Call<CartItemReponse>, t: Throwable) {
                val errorResponse = t.message ?: "Network error"
                listen.onFail(errorResponse)
            }
        })
    }

    private fun callApiList(
        call: Call<CartItemListReponse>,
        listen: OnResultListener<List<CartItem>>
    ) {
        call.enqueue(object : Callback<CartItemListReponse> {
            override fun onResponse(
                call: Call<CartItemListReponse>,
                response: Response<CartItemListReponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        listen.onSuccess(result.cartitems)
                    } else {
                        listen.onFail(Constant.MESS_CALL_API_NOT_FOUND)
                    }
                } else {
                    listen.onFail(Constant.MESS_CALL_API_FAIL)
                }
            }

            override fun onFailure(call: Call<CartItemListReponse>, t: Throwable) {
                val errorResponse = t.message ?: "Network error"
                listen.onFail(errorResponse)
            }
        })
    }

    private fun callApiDelete(call: Call<CartItemReponse>, listen: OnResultListener<String>) {
        call.enqueue(object : Callback<CartItemReponse> {
            override fun onResponse(
                call: Call<CartItemReponse>,
                response: Response<CartItemReponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        listen.onSuccess(result.message)
                    } else {
                        listen.onFail(Constant.MESS_CALL_API_NOT_FOUND)
                    }
                } else {
                    listen.onFail(Constant.MESS_CALL_API_FAIL)
                }
            }

            override fun onFailure(call: Call<CartItemReponse>, t: Throwable) {
                val errorResponse = t.message ?: "Network error"
                listen.onFail(errorResponse)
            }
        })
    }

    companion object {
        private var instance: CallApiCartItem? = null
        fun getInstance() = synchronized(this) {
            instance ?: CallApiCartItem().also { instance = it }
        }
    }
}