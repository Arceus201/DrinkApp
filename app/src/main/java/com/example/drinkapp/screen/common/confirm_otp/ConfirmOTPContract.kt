package com.example.drinkapp.screen.common.confirm_otp

import com.example.drinkapp.utils.base.BasePresenter

interface ConfirmOTPContract {
    interface View{
        fun onSignUpSuccess(msg: String)
        fun onFail(msg: String)

    }
    interface Presenter: BasePresenter<View> {
        fun handlerSignUp(username: String, phone: String, password: String)
    }
}