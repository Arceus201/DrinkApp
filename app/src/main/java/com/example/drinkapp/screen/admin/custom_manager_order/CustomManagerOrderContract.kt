package com.example.drinkapp.screen.admin.custom_manager_order

import com.example.drinkapp.data.model.Order

interface CustomManagerOrderContract {
    interface View{
        fun onGetAllOrderByUserInUserManagerSuccess(list: List<Order>)
        fun onGetAllOrderByUserInUserManagerFail()
        fun onFail(msg: String)
    }
    interface Presenter{
        fun getAllOrderByUserInUserManager(user_id: Long)
    }

}