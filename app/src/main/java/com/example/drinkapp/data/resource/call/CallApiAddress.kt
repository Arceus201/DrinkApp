package com.example.drinkapp.data.resource.call


import com.example.drinkapp.data.model.Address
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.dto.address.AddressDTO
import com.example.drinkapp.data.resource.response.address.AddressListReponse
import com.example.drinkapp.data.resource.response.address.AddressReponse
import com.example.drinkapp.data.resource.retrofit.AddressApiService
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallApiAddress {
    private val addressApiService: AddressApiService by lazy {
        RetrofitHelper.getInstance().create(AddressApiService::class.java)
    }

    fun addAddress(addressDTO: AddressDTO,listen: OnResultListener<Address>){
        val call = addressApiService.addAddress(addressDTO)
        callApi(call,listen)
    }
    fun getAddress(user_id: Long, listen: OnResultListener<List<Address>>){
        val call = addressApiService.getAddress(user_id)
        callApiList(call,listen)
    }

    fun getAddressStore(listen: OnResultListener<Address>){
        val call = addressApiService.getAddressStore()
        callApi(call,listen)
    }


    private fun callApi(call: Call<AddressReponse>, listen: OnResultListener<Address>) {
        call.enqueue(object : Callback<AddressReponse> {
            override fun onResponse(
                call: Call<AddressReponse>,
                response: Response<AddressReponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        listen.onSuccess(result.address)
                    } else {
                        listen.onFail(Constant.MESS_CALL_API_NOT_FOUND)
                    }
                } else {
                    listen.onFail(Constant.MESS_CALL_API_FAIL)
                }
            }

            override fun onFailure(call: Call<AddressReponse>, t: Throwable) {
                val errorResponse = t.message ?: "Network error"
                listen.onFail(errorResponse)
            }
        })
    }
    private fun callApiList(call: Call<AddressListReponse>, listen: OnResultListener<List<Address>>) {
        call.enqueue(object : Callback<AddressListReponse> {
            override fun onResponse(
                call: Call<AddressListReponse>,
                response: Response<AddressListReponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        listen.onSuccess(result.address)
                    } else {
                        listen.onFail(Constant.MESS_CALL_API_NOT_FOUND)
                    }
                } else {
                    listen.onFail(Constant.MESS_CALL_API_FAIL)
                }
            }

            override fun onFailure(call: Call<AddressListReponse>, t: Throwable) {
                val errorResponse = t.message ?: "Network error"
                listen.onFail(errorResponse)
            }
        })
    }
    companion object {
        private var instance: CallApiAddress? = null
        fun getInstance() = synchronized(this) {
            instance ?: CallApiAddress().also { instance = it }
        }
    }

}