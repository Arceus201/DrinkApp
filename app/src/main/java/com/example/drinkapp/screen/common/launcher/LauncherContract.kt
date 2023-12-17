package com.example.drinkapp.screen.common.launcher

interface LauncherContract {
    interface View{
        fun activeAccount()
        fun accountIsBlocked()
    }
    interface Presenter{
        fun checkRoleClient(id: Long)
    }
}