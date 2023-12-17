package com.example.drinkapp.screen.admin.drinkorders

import com.example.drinkapp.data.model.DrinkOrders
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiRevenue

class DrinkOrderPresenter (private val view: DrinkOrderContract.View,
private val callApi: CallApiRevenue):
DrinkOrderContract.Presenter{
    override fun getDrinkOrders(name: String, startTime: String, endTime: String) {
        callApi.getAllDrinkOrders(
            name,startTime,endTime,
            object : OnResultListener<List<DrinkOrders>>{
                override fun onSuccess(list: List<DrinkOrders>) {
                    view.onGetDrinkOrdersSuccess(list)
                }

                override fun onFail(message: String) {
                    view.onGetDrinkOrdersFail()
                }

            }
        )
    }
}