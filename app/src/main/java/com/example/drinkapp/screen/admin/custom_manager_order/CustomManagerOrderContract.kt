package com.example.drinkapp.screen.admin.custom_manager_order

import com.example.drinkapp.data.model.Order
import com.example.drinkapp.utils.base.BasePresenter

interface CustomManagerOrderContract {
    interface View{
        fun onGetAllOrderByUserInUserManagerSuccess(list: List<Order>)
        fun onGetAllOrderByUserInUserManagerFail()
        fun onFail(msg: String)
    }
    interface Presenter : BasePresenter<View> {
        fun getAllOrderByUserInUserManager(user_id: Long)
    }

}