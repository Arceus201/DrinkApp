package com.example.drinkapp.screen.common.launcher

import com.example.drinkapp.utils.base.BasePresenter

interface LauncherContract {
    interface View{
        fun activeAccount()
        fun accountIsBlocked()
    }
    interface Presenter : BasePresenter<View> {
        fun checkRoleClient(id: Long)
    }
}