package com.example.drinkapp.screen.common.profile

import com.example.drinkapp.data.model.User

interface ProfileContract {
    interface View{
        fun onGetUserSuccess(user:User)
        fun onUpdateUserSuccess(user: User)
        fun onFail(msg: String)
    }
    interface Presenter{
        fun getUser(user_id:Long)
        fun updateUser(user_id:Long,username: String, dob: String)
    }
}