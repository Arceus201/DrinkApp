package com.example.drinkapp.screen.admin.custom_manager

import com.example.drinkapp.data.model.User
import com.example.drinkapp.utils.base.BasePresenter

interface CustomManagerContract {
    interface View{
        fun onGetAllClientSuccess(list: List<User>)
        fun onUpdateClientStatusSuccess(list: List<User>)
        fun onGetAllClientFail()

        fun onFail(msg: String)

    }
    interface Presenter : BasePresenter<View> {
        fun getAllClient()
        fun updateClientStatus(id: Long, role: Long)
    }
}