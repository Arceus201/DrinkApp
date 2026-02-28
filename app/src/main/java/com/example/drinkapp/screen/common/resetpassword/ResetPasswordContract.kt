package com.example.drinkapp.screen.common.resetpassword

import com.example.drinkapp.utils.base.BasePresenter

interface ResetPasswordContract {
    interface View{
        fun onUpdatePasswordSuccess()
        fun isValidPasswordPass()
        fun isValidPassworFail()
        fun onFail(msg: String)
    }
    interface Presenter : BasePresenter<View> {
        fun updatePassword(user_id: Long, current_password: String, new_password: String)
        fun checkValidPassword(password: String)
    }
}