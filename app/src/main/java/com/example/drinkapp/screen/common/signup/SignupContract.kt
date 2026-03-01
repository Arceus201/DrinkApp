package com.example.drinkapp.screen.common.signup

import com.example.drinkapp.utils.base.IBasePresenter

interface SignupContract {
    interface View{
        fun onFail(msg: String)
        fun isValidPasswordPass()
        fun isValidPassworFail()

    }
    interface Presenter: IBasePresenter<View> {
        fun checkValidPassword(password: String)
    }
}