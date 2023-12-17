package com.example.drinkapp.screen.common.resetpassword

interface ResetPasswordContract {
    interface View{
        fun onUpdatePasswordSuccess()
        fun isValidPasswordPass()
        fun isValidPassworFail()
        fun onFail(msg: String)
    }
    interface Presenter{
        fun updatePassword(user_id: Long, current_password: String, new_password: String)
        fun checkValidPassword(password: String)
    }
}