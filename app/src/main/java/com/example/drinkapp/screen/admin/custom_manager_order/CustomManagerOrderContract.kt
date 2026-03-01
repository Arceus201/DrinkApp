package com.example.drinkapp.screen.admin.custom_manager_order

import com.example.drinkapp.data.model.Order
import com.example.drinkapp.utils.base.IBasePresenter

interface CustomManagerOrderContract {
    interface View{
        fun onGetAllOrderByUserInUserManagerSuccess(list: List<Order>)
        fun onGetAllOrderByUserInUserManagerFail()
        fun onFail(msg: String)
    }
    interface Presenter : IBasePresenter<View> {
        fun getAllOrderByUserInUserManager(user_id: Long)
    }

}