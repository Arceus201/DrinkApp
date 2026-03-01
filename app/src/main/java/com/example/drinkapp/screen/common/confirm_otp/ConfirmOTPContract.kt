package com.example.drinkapp.screen.common.confirm_otp

import com.example.drinkapp.utils.base.IBasePresenter

interface ConfirmOTPContract {
    interface View{
        fun onSignUpSuccess(msg: String)
        fun onFail(msg: String)

    }
    interface Presenter: IBasePresenter<View> {
        fun handlerSignUp(username: String, phone: String, password: String)
    }
}