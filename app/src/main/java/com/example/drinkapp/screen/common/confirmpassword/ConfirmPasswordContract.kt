package com.example.drinkapp.screen.common.confirmpassword

interface ConfirmPasswordContract {
    interface View{
        fun onConfirmPasswordSuccess()
        fun onFail(msg: String)
    }

    interface Presenter{
        fun conFirmPassword(user_id: Long, password: String)
    }

}