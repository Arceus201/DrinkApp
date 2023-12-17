package com.example.drinkapp.screen.admin.custom_manager

import com.example.drinkapp.data.model.User

interface CustomManagerContract {
    interface View{
        fun onGetAllClientSuccess(list: List<User>)
        fun onUpdateClientStatusSuccess(list: List<User>)
        fun onGetAllClientFail()

        fun onFail(msg: String)

    }
    interface Presenter{
        fun getAllClient()
        fun updateClientStatus(id: Long, role: Long)
    }
}