package com.example.drinkapp.screen.client.order

import com.example.drinkapp.data.model.Order
import com.example.drinkapp.utils.base.IBasePresenter

interface OrderContract {
    interface View{
        fun onGetAllOrderSuccess(list: List<Order>)
        fun onGetAllOrderFail()
        fun onFail(msg: String)
    }
    interface Presenter : IBasePresenter<View> {
        fun getOrder(user_id: Long)
    }
}