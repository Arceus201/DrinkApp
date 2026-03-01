package com.example.drinkapp.screen.common.launcher

import com.example.drinkapp.utils.base.IBasePresenter

interface LauncherContract {
    interface View{
        fun activeAccount()
        fun accountIsBlocked()
    }
    interface Presenter : IBasePresenter<View> {
        fun checkRoleClient(id: Long)
    }
}