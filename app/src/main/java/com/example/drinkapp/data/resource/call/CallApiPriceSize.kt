package com.example.drinkapp.data.resource.call

import com.example.drinkapp.data.model.PriceSize
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.dto.pricesize.PriceSizeDTO
import com.example.drinkapp.data.resource.response.pricesize.PriceSizeListReponse
import com.example.drinkapp.data.resource.response.pricesize.PriceSizeReponse
import com.example.drinkapp.data.resource.retrofit.PriceSizeApiService
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallApiPriceSize {
    private val priceSizeApiService: PriceSizeApiService by lazy {
        RetrofitHelper.getInstance().create(PriceSizeApiService::class.java)
    }
    fun getAllPriceSizeByProductID(id: Long,listen: OnResultListener<List<PriceSize>>){
        val call = priceSizeApiService.getAllPriceSizeByProductID(id)
        callApiList(call,listen)
    }
    fun getAllPriceSizeClientByProductID(id: Long,listen: OnResultListener<List<PriceSize>>){
        val call = priceSizeApiService.getAllPriceSizeClientByProductID(id)
        callApiList(call,listen)
    }


    fun addPriceSize(idProduct: Long,idSize: Long, price: Double,listen: OnResultListener<List<PriceSize>>) {
        val priceSizeDTO = PriceSizeDTO(idProduct,idSize,price,1L)
        val call = priceSizeApiService.addPriceSize(priceSizeDTO)
        callApiList(call,listen)
    }

    fun updatePriceSizeDefault(idProduct: Long,idSize: Long, price: Double,status: Long,listen: OnResultListener<PriceSize>) {
        val priceSizeDTO = PriceSizeDTO(idProduct,idSize,price,status)
        val call = priceSizeApiService.updatePriceSizeDefault(priceSizeDTO)
        callApi(call,listen)
    }

    fun updatePriceSize(id: Long,idProduct: Long,idSize: Long, price: Double,status: Long,listen: OnResultListener<List<PriceSize>>) {
        val priceSizeDTO = PriceSizeDTO(idProduct,idSize,price,status)
        val call = priceSizeApiService.updatePriceSize(id,priceSizeDTO)
        callApiList(call,listen)
    }
    private fun callApi(call: Call<PriceSizeReponse>, listen: OnResultListener<PriceSize>) {
        call.enqueue(object : Callback<PriceSizeReponse> {
            override fun onResponse(
                call: Call<PriceSizeReponse>,
                response: Response<PriceSizeReponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        listen.onSuccess(result.pricesize)
                    } else {
                        listen.onFail("Add PriceSize Default không thành công")
                    }
                } else {
                    listen.onFail(Constant.MESS_CALL_API_FAIL)
                }
            }

            override fun onFailure(call: Call<PriceSizeReponse>, t: Throwable) {
                val errorResponse = t.message ?: "Network error"
                listen.onFail(errorResponse)
            }
        })
    }

    private fun callApiList(call: Call<PriceSizeListReponse>, listen: OnResultListener<List<PriceSize>>) {
        call.enqueue(object : Callback<PriceSizeListReponse> {
            override fun onResponse(
                call: Call<PriceSizeListReponse>,
                response: Response<PriceSizeListReponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        listen.onSuccess(result.pricesizes)
                    } else {
                        listen.onFail(Constant.MESS_CALL_API_NOT_FOUND)
                    }
                } else {
                    listen.onFail(Constant.MESS_CALL_API_FAIL)
                }
            }

            override fun onFailure(call: Call<PriceSizeListReponse>, t: Throwable) {
                val errorResponse = t.message ?: "Network error"
                listen.onFail(errorResponse)
            }
        })
    }

    companion object{
        private var instance: CallApiPriceSize? = null
        fun getInstance() = synchronized(this){
            instance?: CallApiPriceSize().also { instance = it }
        }
    }
}