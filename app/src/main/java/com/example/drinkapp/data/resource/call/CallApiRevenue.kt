package com.example.drinkapp.data.resource.call

import com.example.drinkapp.data.model.DrinkOrders
import com.example.drinkapp.data.model.RevenueStatistics
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.response.drinkorders.DrinkOrdersReponse
import com.example.drinkapp.data.resource.response.revenue.RevenueStatisticsReponse
import com.example.drinkapp.data.resource.retrofit.RevenueApiService
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallApiRevenue {
    private val revenueApiService: RevenueApiService by lazy {
        RetrofitHelper.getInstance().create(RevenueApiService::class.java)
    }
    fun getRevenueStatistics(startTime: String, endTime: String,listen: OnResultListener<List<RevenueStatistics>>){
        val call = revenueApiService.getRevenueStatistics(startTime,endTime)
        callApiRevenueList(call,listen)
    }
    fun getAllDrinkOrders(name: String,startTime: String, endTime: String,listen: OnResultListener<List<DrinkOrders>>){
        val call = revenueApiService.getAllDrinkOrders(name,startTime,endTime);
        callApiDrinkOrderList(call,listen)
    }

    private fun callApiRevenueList(call: Call<RevenueStatisticsReponse>, listen: OnResultListener<List<RevenueStatistics>>) {
        call.enqueue(object : Callback<RevenueStatisticsReponse> {
            override fun onResponse(
                call: Call<RevenueStatisticsReponse>,
                response: Response<RevenueStatisticsReponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        listen.onSuccess(result.revenue)
                    } else {
                        listen.onFail(Constant.MESS_CALL_API_NOT_FOUND)
                    }
                } else {
                    listen.onFail(Constant.MESS_CALL_API_FAIL)
                }
            }

            override fun onFailure(call: Call<RevenueStatisticsReponse>, t: Throwable) {
                val errorResponse = t.message ?: "Network error"
                listen.onFail(errorResponse)
            }
        })
    }

    private fun callApiDrinkOrderList(call: Call<DrinkOrdersReponse>, listen: OnResultListener<List<DrinkOrders>>) {
        call.enqueue(object : Callback<DrinkOrdersReponse> {
            override fun onResponse(
                call: Call<DrinkOrdersReponse>,
                response: Response<DrinkOrdersReponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        listen.onSuccess(result.drinkOrders)
                    } else {
                        listen.onFail(Constant.MESS_CALL_API_NOT_FOUND)
                    }
                } else {
                    listen.onFail(Constant.MESS_CALL_API_FAIL)
                }
            }

            override fun onFailure(call: Call<DrinkOrdersReponse>, t: Throwable) {
                val errorResponse = t.message ?: "Network error"
                listen.onFail(errorResponse)
            }
        })
    }

    companion object {
        private var instance: CallApiRevenue? = null
        fun getInstance() = synchronized(this) {
            instance ?: CallApiRevenue().also { instance = it }
        }
    }
}