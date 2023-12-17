package com.example.drinkapp.data.resource.call

import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.response.ExchangeRateReponse
import com.example.drinkapp.data.resource.retrofit.ExChangeRateApiService
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.RetrofitHeplerRate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExchangeRateAPI {
    private val exChangeRateApiService: ExChangeRateApiService by lazy {
        RetrofitHeplerRate.getInstance().create(ExChangeRateApiService::class.java)
    }

    fun getExchangeRates(listen: OnResultListener<Double>) {
        val call = exChangeRateApiService.getExchangeRates()
        callApi(call, listen)
    }

    private fun callApi(call: Call<ExchangeRateReponse>, listen: OnResultListener<Double>) {
        call.enqueue(object : Callback<ExchangeRateReponse> {
            override fun onResponse(
                call: Call<ExchangeRateReponse>,
                response: Response<ExchangeRateReponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        result.conversion_rates["VND"]?.let { listen.onSuccess(it) }
                    } else {
                        listen.onFail(Constant.MESS_CALL_API_NOT_FOUND)
                    }
                } else {
                    listen.onFail(Constant.MESS_CALL_API_FAIL)
                }
            }

            override fun onFailure(call: Call<ExchangeRateReponse>, t: Throwable) {
                val errorResponse = t.message ?: "Network error"
                listen.onFail(errorResponse)
            }
        })
    }

    companion object {
        private var instance: ExchangeRateAPI? = null
        fun getInstance() = synchronized(this) {
            instance ?: ExchangeRateAPI().also { instance = it }
        }
    }
}
