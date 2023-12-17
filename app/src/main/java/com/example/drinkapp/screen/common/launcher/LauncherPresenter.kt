package com.example.drinkapp.screen.common.launcher

import com.example.drinkapp.data.model.User
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiUser

class LauncherPresenter(
    private val view: LauncherContract.View,
    private val callApi: CallApiUser
) :
    LauncherContract.Presenter {
    override fun checkRoleClient(id: Long) {
        callApi.getUserById(
            id,
            object : OnResultListener<User>{
                override fun onSuccess(list: User) {
                    if(list.role == 1L)  view.activeAccount()
                    else view.accountIsBlocked()
                }

                override fun onFail(message: String) {

                }

            }
        )
    }
}