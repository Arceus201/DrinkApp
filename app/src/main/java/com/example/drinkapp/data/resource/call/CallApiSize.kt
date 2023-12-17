package com.example.drinkapp.data.resource.call

import com.example.drinkapp.data.model.Size
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.dto.size.SizeDTO
import com.example.drinkapp.data.resource.response.size.SizeResponse
import com.example.drinkapp.data.resource.retrofit.SizeApiService
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallApiSize {
    private val sizeApiService: SizeApiService by lazy {
        RetrofitHelper.getInstance().create(SizeApiService::class.java)
    }

    fun getAllSize(listen: OnResultListener<List<Size>>) {
        val call = sizeApiService.getall()
        callApi(call,listen)
    }

    fun addSize(name: String,listen: OnResultListener<List<Size>>) {
        val sizeDTO = SizeDTO(name)
        val call = sizeApiService.addSize(sizeDTO)
        callApi(call,listen)
    }

    fun updateSize(id: Long?,size: Size,listen: OnResultListener<List<Size>>) {
        val call = sizeApiService.updateSize(id,size)
        callApi(call,listen)
    }

    fun callApi(call: Call<SizeResponse>,listen: OnResultListener<List<Size>>){
        call.enqueue(object : Callback<SizeResponse> {
            override fun onResponse(
                call: Call<SizeResponse>, response: Response<SizeResponse>
            ) {
                if (response.isSuccessful) {
                    val sizesResponse = response.body()
                    if (sizesResponse != null) {
                        val sizes = sizesResponse.sizes
                        if (sizes != null && sizes.isNotEmpty()) listen.onSuccess(sizes)
                    } else {
                        listen.onFail(Constant.MESS_CALL_API_NOT_FOUND)
                    }
                } else {
                    listen.onFail(Constant.MESS_CALL_API_FAIL)
                }
            }

            override fun onFailure(call: Call<SizeResponse>, t: Throwable) {
                val errorResponse = t.message ?: "Network error"
                listen.onFail(errorResponse)
            }
        })
    }
    companion object{
        private var instance: CallApiSize? = null
        fun getInstance() = synchronized(this){
            instance?: CallApiSize().also { instance = it }
        }
    }
}