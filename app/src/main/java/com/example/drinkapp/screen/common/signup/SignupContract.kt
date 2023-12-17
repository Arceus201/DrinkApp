package com.example.drinkapp.screen.common.signup

import com.example.drinkapp.utils.base.BasePresenter

interface SignupContract {
    interface View{
        fun onFail(msg: String)
        fun isValidPasswordPass()
        fun isValidPassworFail()

    }
    interface Presenter: BasePresenter<View> {
        fun checkValidPassword(password: String)
    }
}