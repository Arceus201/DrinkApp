package com.example.drinkapp.data.resource.call
import com.example.drinkapp.data.model.addressVN.City
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.retrofit.AddressVNpiService
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.RetrofitHepplerAddressVN
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallApiAddressVN {
    private val addressVNApiService: AddressVNpiService by lazy {
        RetrofitHepplerAddressVN.getInstance().create(AddressVNpiService::class.java)
    }


    fun getAddressVN( listen: OnResultListener<List<City>>){
        val call = addressVNApiService.getAddressVN()
        callApi(call,listen)
    }

    private fun callApi(call: Call<List<City>>, listen: OnResultListener<List<City>>) {
        call.enqueue(object : Callback<List<City>> {
            override fun onResponse(
                call: Call<List<City>>,
                response: Response<List<City>>
            ) {
                if (response.isSuccessful) {
                    val result: List<City>? = response.body()
                    if (result != null) {
                        listen.onSuccess(result)
                    } else {
                        listen.onFail(Constant.MESS_CALL_API_NOT_FOUND)
                    }
                } else {
                    listen.onFail(Constant.MESS_CALL_API_FAIL)
                }
            }

            override fun onFailure(call: Call<List<City>>, t: Throwable) {
                val errorResponse = t.message ?: "Network error"
                listen.onFail(errorResponse)
            }
        })
    }
    companion object {
        private var instance: CallApiAddressVN? = null
        fun getInstance() = synchronized(this) {
            instance ?: CallApiAddressVN().also { instance = it }
        }
    }
}