package com.example.drinkapp.screen.admin.drinkorders

import com.example.drinkapp.data.model.DrinkOrders
import com.example.drinkapp.utils.base.BasePresenter

interface DrinkOrderContract {
    interface View{
        fun onGetDrinkOrdersSuccess(list: List<DrinkOrders>)
        fun onGetDrinkOrdersFail()
        fun onFail(msg: String)
    }
    interface Presenter : BasePresenter<View> {
        fun getDrinkOrders(name: String, startTime: String, endTime: String)
    }
}