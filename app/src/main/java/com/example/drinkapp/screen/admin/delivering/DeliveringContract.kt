package com.example.drinkapp.screen.admin.delivering

import com.example.drinkapp.data.model.Order
import com.example.drinkapp.utils.base.IBasePresenter

interface DeliveringContract {
    interface View{
        fun onGetListOrderSuccess(list: List<Order>)
        fun onFail(msg: String)
        fun onGetListOrderFail()
    }
    interface Presenter : IBasePresenter<View> {
        fun getListOrder(order_status: Long)
    }
}