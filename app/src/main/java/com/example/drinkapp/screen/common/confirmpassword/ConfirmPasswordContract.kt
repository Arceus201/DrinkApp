package com.example.drinkapp.screen.common.confirmpassword

import com.example.drinkapp.utils.base.BasePresenter

interface ConfirmPasswordContract {
    interface View{
        fun onConfirmPasswordSuccess()
        fun onFail(msg: String)
    }

    interface Presenter : BasePresenter<View> {
        fun conFirmPassword(user_id: Long, password: String)
    }

}