package com.example.drinkapp.screen.common.login

import com.example.drinkapp.data.model.User
import com.example.drinkapp.utils.base.IBasePresenter

interface LoginContract {
    interface View{
        fun onLoginSuccess(user: User)
        fun onLoginFail(msg: String)
    }
    interface Presenter: IBasePresenter<View>{
        fun handleLogin(phone: String,password: String)
    }
}